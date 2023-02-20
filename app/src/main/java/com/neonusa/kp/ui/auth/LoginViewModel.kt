package com.neonusa.kp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.neonusa.kp.data.DataRepository
import com.neonusa.kp.data.request.LoginRequest

class LoginViewModel(): ViewModel() {
    val dataRepository = DataRepository()
    fun login(data: LoginRequest) = dataRepository.login(data).asLiveData()
}