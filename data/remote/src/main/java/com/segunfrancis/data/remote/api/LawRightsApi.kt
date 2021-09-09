package com.segunfrancis.data.remote.api

import com.segunfrancis.data.remote.model.LoginRequest
import com.segunfrancis.data.remote.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LawRightsApi {

    @POST("api/client_login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("api/rights")
    suspend fun getAllRights(@Query("page") page: Int)
}
