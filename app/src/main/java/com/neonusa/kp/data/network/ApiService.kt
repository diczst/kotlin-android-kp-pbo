package com.neonusa.kp.data.network

import com.neonusa.kp.data.model.Materi
import com.neonusa.kp.data.model.User
import com.neonusa.kp.data.request.LoginRequest
import com.neonusa.kp.data.request.SelesaiMateriRequest
import com.neonusa.kp.data.request.UpdateUserRequest
import com.neonusa.kp.data.response.BaseListResponse
import com.neonusa.kp.data.response.BaseSingleResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("login")
    suspend fun login(
        @Body data: LoginRequest
    ): Response<BaseSingleResponse<User>>

    @Multipart
    @POST("user")
    suspend fun register(
        @Part("nama_lengkap") nik: RequestBody,
        @Part("nisn") nama: RequestBody,
        @Part("no_hp") no_handphone: RequestBody,
        @Part("password") password: RequestBody
    ): Response<BaseSingleResponse<User>>

    @GET("user/{id}")
    suspend fun getDataUser(
        @Path("id") id: String? = null
    ): Response<BaseSingleResponse<User>>

    // @path harus sama dengan didalam kurawal
    @PUT("user/{id}")
    suspend fun updateUser(
        @Path("id") int: Int,
        @Body data: UpdateUserRequest
    ): Response<BaseSingleResponse<User>>

    @Multipart
    @POST("upload-user/{id}")
    suspend fun uploadUser(
        @Path("id") int: Int?,
        @Part data: MultipartBody.Part? = null
    ): Response<BaseSingleResponse<User>>

    @GET("user")
    suspend fun getUsers(): Response<BaseListResponse<User>>

    @GET("materi")
    suspend fun getMateris(): Response<BaseListResponse<Materi>>

    @GET("materi/{id}")
    suspend fun getMateri(
        @Path("id") id: String? = null
    ): Response<BaseSingleResponse<Materi>>

    @POST("selesai-materi/{id}")
    suspend fun selesaiMateri(
        @Path("id") int: Int,
        @Body data: SelesaiMateriRequest
    ): Response<BaseSingleResponse<User>>
}