package com.neonusa.kp.data.network

import com.neonusa.kp.data.model.Materi
import com.neonusa.kp.data.model.Soal
import com.neonusa.kp.data.model.Tantangan
import com.neonusa.kp.data.model.Siswa
import com.neonusa.kp.data.request.LoginRequest
import com.neonusa.kp.data.request.TambahExpRequest
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
    ): Response<BaseSingleResponse<Siswa>>

    @Multipart
    @POST("siswa")
    suspend fun register(
        @Part("nama_lengkap") nik: RequestBody,
        @Part("nisn") nama: RequestBody,
        @Part("no_hp") no_handphone: RequestBody,
        @Part("password") password: RequestBody
    ): Response<BaseSingleResponse<Siswa>>

    @GET("siswa/{id}")
    suspend fun getDataUser(
        @Path("id") id: String? = null
    ): Response<BaseSingleResponse<Siswa>>

    // @path harus sama dengan didalam kurawal
    @PUT("siswa/{id}")
    suspend fun updateUser(
        @Path("id") int: Int,
        @Body data: UpdateUserRequest
    ): Response<BaseSingleResponse<Siswa>>

    @Multipart
    @POST("upload-siswa/{id}")
    suspend fun uploadUser(
        @Path("id") int: Int?,
        @Part data: MultipartBody.Part? = null
    ): Response<BaseSingleResponse<Siswa>>

    @GET("siswa")
    suspend fun getUsers(): Response<BaseListResponse<Siswa>>

    @GET("materi")
    suspend fun getMateris(): Response<BaseListResponse<Materi>>

    @GET("materi/{id}")
    suspend fun getMateri(
        @Path("id") id: String? = null
    ): Response<BaseSingleResponse<Materi>>

    @POST("tambah-exp/{id}")
    suspend fun tambahExp(
        @Path("id") int: Int,
        @Body data: TambahExpRequest
    ): Response<BaseSingleResponse<Siswa>>

    @GET("tantangan/{materi_id}")
    suspend fun getTantangan(
        @Path("materi_id") id: String? = null
    ): Response<BaseListResponse<Tantangan>>

    @GET("soal/{tantangan_id}")
    suspend fun getSoal(
        @Path("tantangan_id") id: String? = null
    ): Response<BaseListResponse<Soal>>
}