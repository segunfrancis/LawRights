package com.segunfrancis.data.remote.repository

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.segunfrancis.data.remote.api.LawRightsApi
import com.segunfrancis.data.remote.model.LoginResponse
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class RemoteRepositoryTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val mockWebServer = MockWebServer()

    private val mockPreference = mockk<SharedPreferences>()

    private val mockClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .build()

    private val mockGson = GsonBuilder().setLenient().create()

    private val mockApi = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/").toString())
        .client(mockClient)
        .addConverterFactory(GsonConverterFactory.create(mockGson))
        .build()
        .create(LawRightsApi::class.java)

    private val sut = RemoteRepositoryImpl(mockApi, mockPreference, Dispatchers.Main)

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun `should return correct response when login is successful`() {
        mockWebServer.enqueueResponse("login_response.json", 200)
        runBlocking {
            launch(Dispatchers.Main) {
                val actual = sut.login()
                val expected = LoginResponse(
                    access_token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wva21yLXN0YWdpbmcubGF3cGF2aWxpb24uY29tXC9hcGlcL2NsaWVudF9sb2dpbiIsImlhdCI6MTYzMTExNTEyOSwiZXhwIjoxNjMxMTE1NzI5LCJuYmYiOjE2MzExMTUxMjksImp0aSI6IjdEd2g2d1F0VEdxS0EwYTMiLCJzdWIiOjMsInBydiI6ImUxMGRhZThjYzlkN2MyY2VhMTI4ODQ4NWYyMTA1N2Y4M2U4NmU0MjEifQ.5ewhFoNFh55QLiGCXeFWba6yKKl4CKAZs0QO1jiU85w",
                    token_type = "bearer",
                    expires_in = 600
                )
                assertEquals(expected, actual)
            }
        }
    }

    private fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)

        val source = inputStream?.let { inputStream.source().buffer() }
        source?.let {
            enqueue(
                MockResponse()
                    .setResponseCode(code)
                    .setBody(source.readString(StandardCharsets.UTF_8))
            )
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }
}