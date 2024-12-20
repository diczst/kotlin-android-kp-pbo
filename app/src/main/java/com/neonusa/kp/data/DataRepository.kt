package com.neonusa.kp.data

import com.neonusa.kp.Kotpreference
import com.neonusa.kp.data.network.ApiConfig
import com.neonusa.kp.data.network.ApiService
import com.neonusa.kp.data.network.Resource
import com.neonusa.kp.data.request.*
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
        password: RequestBody,
        ttl: RequestBody
    ) = flow {
        emit(Resource.loading(null))
        try {
            apiService.register(nisn, nama_lengkap, no_hp, password, ttl).let {
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

    fun updateLevelTantanganUser(data: AddLevelTantanganUserRequest) = flow {
        emit(Resource.loading(null))
        try {
            apiService.updateLevelTantanganUser(data.id, data).let {
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

    fun updateLevelMateriUser(data: AddLevelMateriUserRequest) = flow {
        emit(Resource.loading(null))
        try {
            apiService.updateLevelMateriUser(data.id, data).let {
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

    fun getMateris() = flow {
        emit(Resource.loading(null))
        try {
            apiService.getMateris().let {
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

    fun getDataMateri(id: String?) = flow {
        emit(Resource.loading(null))
        try {
            apiService.getMateri(id).let {
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

    fun tambahExp(data: TambahExpRequest) = flow {
        emit(Resource.loading(null))
        try {
            apiService.tambahExp(data.id, data).let {
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

    fun updateCoin(data: UpdateCoinRequest) = flow {
        emit(Resource.loading(null))
        try {
            apiService.updateCoin(data.id, data).let {
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

    fun getTantangan(id: String?) = flow {
        emit(Resource.loading(null))
        try {
            apiService.getTantangan(id).let {
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

    fun getSoal(id: String?) = flow {
        emit(Resource.loading(null))
        try {
            apiService.getSoal(id).let {
                if (it.isSuccessful) {
                    val body = it.body()
                    var data = body?.data

                    // jika sudah percobaan ketiga maka acak soal
                    // kenapa di acak?
                    // agar lebih menantang dan aplikasi lebih dinamis (tidak statis)
                    // todo : ランダム
                    if(Kotpreference.tryCount == 3) {
                        data = body?.data?.shuffled()?.take(5)
                    } else {
                        data = data?.take(5)
                    }

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
}