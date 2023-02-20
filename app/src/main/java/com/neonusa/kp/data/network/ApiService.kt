package com.neonusa.kp.data.network

import com.neonusa.kp.data.model.User
import com.neonusa.kp.data.request.LoginRequest
import com.neonusa.kp.data.response.BaseSingleResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("login")
    suspend fun login(
        @Body data: LoginRequest
    ): Response<BaseSingleResponse<User>>
}