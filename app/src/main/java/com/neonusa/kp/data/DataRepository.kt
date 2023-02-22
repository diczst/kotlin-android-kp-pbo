package com.neonusa.kp.data

import com.neonusa.kp.Kotpreference
import com.neonusa.kp.data.network.ApiConfig
import com.neonusa.kp.data.network.ApiService
import com.neonusa.kp.data.network.Resource
import com.neonusa.kp.data.request.LoginRequest
import com.neonusa.kp.data.request.UpdateUserRequest
import com.neonusa.kp.getErrorBody
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class DataRepository {
    private val apiService: ApiService = ApiConfig.provideApiService

    fun login(data: LoginRequest) = flow{
        emit(Resource.loading(null))
        try {
            apiService.login(data).let {
                if (it.isSuccessful) {
                    Kotpreference.isLogin = true
                    val body = it.body()
                    val user = body?.data
                    Kotpreference.setUser(user)
                    emit(Resource.success(user))
                } else {
                    emit(Resource.error( it.getErrorBody()?.message ?: "Error Default", null))
                }
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Terjadi Kesalahan", null))
        }
    }

    fun register(
        nama_lengkap: RequestBody,
        nisn: RequestBody,
        no_hp: RequestBody,
        password: RequestBody
    ) = flow {
        emit(Resource.loading(null))
        try {
            apiService.register(nisn, nama_lengkap, no_hp, password).let {
                if (it.isSuccessful) {
                    Kotpreference.isLogin = true
                    val body = it.body()
                    val user = body?.data

                    Kotpreference.setUser(user)
                    emit(Resource.success(body))
                } else {
                    emit(Resource.error(it.getErrorBody()?.message ?: "Default error dongs", null))
                }
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Terjadi Kesalahan", null))
        }
    }

    fun getDataUser(id: String?) = flow {
        emit(Resource.loading(null))
        try {
            apiService.getDataUser(id).let {
                if (it.isSuccessful) {
                    val body = it.body()
                    val data = body?.data
                    if(data != null){
                        emit(Resource.success(data))
                    } else {
                        emit(Resource.error("response berhasil tapi data null",null))
                    }

                } else {
                    emit(Resource.error(it.getErrorBody()?.message ?: "Default error dongs", null))
                }
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Terjadi Kesalahan", null))
        }
    }

    fun updateUser(data: UpdateUserRequest) = flow {
        emit(Resource.loading(null))
        try {
            apiService.updateUser(data.id, data).let {
                if (it.isSuccessful) {
                    val body = it.body()
                    val user = body?.data
                    Kotpreference.setUser(user)
                    emit(Resource.success(user))
                } else {
                    emit(Resource.error(it.getErrorBody()?.message ?: "Default error dongs", null))
                }
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Terjadi Kesalahan", null))
        }
    }

    fun uploadUser(id: Int? = null, fileImage: MultipartBody.Part? = null) = flow {
        emit(Resource.loading(null))
        try {
            apiService.uploadUser(id, fileImage).let {
                if (it.isSuccessful) {
                    val body = it.body()
                    val user = body?.data
                    Kotpreference.setUser(user)
                    emit(Resource.success(user))
                } else {
                    emit(Resource.error(it.getErrorBody()?.message ?: "Default error dongs", null))
                }
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Terjadi Kesalahan", null))
        }
    }

    fun getUsers() = flow {
        emit(Resource.loading(null))
        try {
            apiService.getUsers().let {
                if (it.isSuccessful) {
                    val body = it.body()
                    val data = body?.data
                    emit(Resource.success(data))
                } else {
                    emit(Resource.error(it.getErrorBody()?.message ?: "Default error dongs", null))
                }
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Terjadi Kesalahan", null))
        }
    }
}