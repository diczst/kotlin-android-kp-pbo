package com.neonusa.kp.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.neonusa.kp.data.DataRepository
import com.neonusa.kp.data.request.UpdateUserRequest
import okhttp3.MultipartBody

class AccountViewModel: ViewModel() {
    val dataRepository = DataRepository()
    fun getDataUser(id:String?) = dataRepository.getDataUser(id).asLiveData()
    fun update(data: UpdateUserRequest) = dataRepository.updateUser(data).asLiveData()
    fun uploadUser(id: Int? = null, fileImage: MultipartBody.Part? = null) = dataRepository.uploadUser(id, fileImage).asLiveData()

}