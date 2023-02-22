package com.neonusa.kp.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.neonusa.kp.data.DataRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RegisterViewModel: ViewModel() {
    private val dataRepository = DataRepository()
    fun register(        nama_lengkap : RequestBody,
                         nisn: RequestBody,
                         no_hp : RequestBody,
                         password: RequestBody) =
        dataRepository.register(
            nama_lengkap, nisn, no_hp,  password
        ).asLiveData()
}