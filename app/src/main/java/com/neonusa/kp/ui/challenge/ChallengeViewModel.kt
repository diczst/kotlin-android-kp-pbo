package com.neonusa.kp.ui.challenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.neonusa.kp.data.DataRepository
import com.neonusa.kp.data.DummyData
import com.neonusa.kp.data.request.TambahExpRequest
import com.neonusa.kp.data.request.UpdateCoinRequest

class ChallengeViewModel: ViewModel() {
    private val dataRepository = DataRepository()
//    fun getListSoal() = DummyData.listSoal
    fun getListTantangan(materi_id: String?) = dataRepository.getTantangan(materi_id).asLiveData()
    fun getListSoal(tantangan_id: String?) = dataRepository.getSoal(tantangan_id).asLiveData()
    fun tambahExp(data: TambahExpRequest) = dataRepository.tambahExp(data).asLiveData()
    fun updateCoin(data: UpdateCoinRequest) = dataRepository.updateCoin(data).asLiveData()
}