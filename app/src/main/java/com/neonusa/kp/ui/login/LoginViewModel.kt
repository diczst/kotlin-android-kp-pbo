package com.neonusa.kp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.neonusa.kp.data.DataRepository
import com.neonusa.kp.data.request.LoginRequest

class LoginViewModel(): ViewModel() {
    private val dataRepository = DataRepository()
    fun login(data: LoginRequest) = dataRepository.login(data).asLiveData()
}