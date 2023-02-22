package com.neonusa.kp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.neonusa.kp.data.DataRepository

class HomeViewModel : ViewModel() {
    val dataRepository = DataRepository()
    fun getDataUser(id:String?) = dataRepository.getDataUser(id).asLiveData()
}