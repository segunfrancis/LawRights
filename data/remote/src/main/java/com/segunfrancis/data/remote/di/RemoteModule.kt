package com.segunfrancis.data.remote.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.segunfrancis.data.remote.api.LawRightsApi
import com.segunfrancis.data.remote.util.RemoteConstants.BASE_URL
import com.segunfrancis.data.remote.util.RemoteConstants.NETWORK_TIMEOUT
import com.segunfrancis.data.remote.util.RemoteConstants.SHARED_PREF_KEY
import com.segunfrancis.data.remote.util.RemoteConstants.TOKEN_PREF_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideToken(preferences: SharedPreferences): String? {
        return preferences.getString(TOKEN_PREF_KEY, null)
    }

    @Provides
    fun provideHeaderInterceptor(token: String?): Interceptor {
        return if (token != null) Interceptor {
            val original: Request = it.request()
            val request: Request = original.newBuilder()
                .header("Bearer", token)
                .method(original.method, original.body)
                .build()
            it.proceed(request)
        } else {
            Interceptor {
                it.proceed(it.request().newBuilder().build())
            }
        }
    }

    @Provides
    fun provideClient(loggingInterceptor: HttpLoggingInterceptor, interceptor: Interceptor): OkHttpClient {
        return OkHttpClient().newBuilder()
            .callTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(interceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideLawRightsApi(retrofit: Retrofit): LawRightsApi {
        return retrofit.create(LawRightsApi::class.java)
    }

    @Provides
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}
