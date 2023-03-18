package com.neonusa.kp.ui.detailmateri

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.neonusa.kp.data.DataRepository
import com.neonusa.kp.data.request.TambahExpRequest

class DetailMateriViewModel: ViewModel() {
    private val dataRepository = DataRepository()
    fun getDataMateri(id:String?) = dataRepository.getDataMateri(id).asLiveData()
    fun tambahExp(data: TambahExpRequest) = dataRepository.tambahExp(data).asLiveData()

}