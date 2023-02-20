package com.neonusa.kp.data

import android.util.Log
import com.example.ppllapasfix.data.network.ApiConfig
import com.neonusa.kp.Kotpreference
import com.neonusa.kp.data.network.ApiService
import com.neonusa.kp.data.network.Resource
import com.neonusa.kp.data.request.LoginRequest
import com.neonusa.kp.getErrorBody
import kotlinx.coroutines.flow.flow

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
                    Log.e("Login Error : ", it.message())
                }
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Terjadi Kesalahan", null))
            Log.e("Login Error : ", e.message ?: "Terjadi Kesalahan")
        }
    }

}