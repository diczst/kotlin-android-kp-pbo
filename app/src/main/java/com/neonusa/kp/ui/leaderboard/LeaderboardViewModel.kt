package com.neonusa.kp.ui.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.neonusa.kp.data.DataRepository

class LeaderboardViewModel : ViewModel() {
    // dummy data untuk testing
//    val listUsers: LiveData<List<User>> = MutableLiveData<List<User>>().apply {
//        value = DummyData.listUser
//    }

    val dataRepository = DataRepository()
    fun getUsers() = dataRepository.getUsers().asLiveData()
    fun getDataUser(id:String?) = dataRepository.getDataUser(id).asLiveData()
}