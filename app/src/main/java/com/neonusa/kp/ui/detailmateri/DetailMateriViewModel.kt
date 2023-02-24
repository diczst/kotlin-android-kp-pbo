package com.neonusa.kp.ui.detailmateri

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.neonusa.kp.data.DataRepository
import com.neonusa.kp.data.request.SelesaiMateriRequest
import com.neonusa.kp.data.request.UpdateUserRequest
import okhttp3.MultipartBody

class DetailMateriViewModel: ViewModel() {
    private val dataRepository = DataRepository()
    fun getDataMateri(id:String?) = dataRepository.getDataMateri(id).asLiveData()
    fun selesaiMateri(data: SelesaiMateriRequest) = dataRepository.selesaiMateri(data).asLiveData()

}