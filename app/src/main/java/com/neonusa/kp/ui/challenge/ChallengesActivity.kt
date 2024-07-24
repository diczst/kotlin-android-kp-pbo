package com.neonusa.kp.ui.challenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.neonusa.kp.Kotpreference
import com.neonusa.kp.adapter.TantanganAdapter
import com.neonusa.kp.data.model.Tantangan
import com.neonusa.kp.data.network.Resource
import com.neonusa.kp.databinding.ActivityChallengesBinding
import com.techiness.progressdialoglibrary.ProgressDialog

class ChallengesActivity : AppCompatActivity() {
    companion object {
        const val MATERI_ID = "MATERI_ID"
        const val MATERI_LEVEL = "MATERI_LEVEL"
        const val MATERI_NAMA = "MATERI_NAMA"
        const val MATERI_LEVEL_USER = "MATERI_LEVEL_USER"
        const val TANTANGAN_LEVEL_USER = "TANTANGAN_LEVEL_USER"
    }

    private lateinit var binding: ActivityChallengesBinding
    private lateinit var viewModel: ChallengeViewModel
    private lateinit var progressDialog: ProgressDialog

    private val tantanganAdapter = TantanganAdapter()

    var id = 0
    var materiLevel = 0
    var userMateriLevel = 0
    var userTantanganLevel = 0
    var materiNama = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChallengesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)
        viewModel = ViewModelProvider(this)[ChallengeViewModel::class.java]

        id = intent.getIntExtra(MATERI_ID,0)
        materiLevel = intent.getIntExtra(MATERI_LEVEL,0)
        materiNama = intent.getStringExtra(MATERI_NAMA).toString()
        userMateriLevel = intent.getIntExtra(MATERI_LEVEL_USER,0)
        userTantanganLevel = intent.getIntExtra(TANTANGAN_LEVEL_USER,0)

        binding.tvTitle.text = materiNama

        tantanganAdapter.materiLevel = materiLevel
        tantanganAdapter.userTantanganLevel = userTantanganLevel
        tantanganAdapter.userMateriLevel = userMateriLevel

        // debug
//        Toast.makeText(this, "materi level ${materiLevel}\nusertlevel ${userTantanganLevel}\nusermlevel ${userMateriLevel}", Toast.LENGTH_SHORT).show()

        binding.rvChallenge.adapter = tantanganAdapter
        binding.rvChallenge.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        getTantangan()
    }

    private fun getTantangan(){
        viewModel.getListTantangan(id.toString()).observe(this){
            when (it.state) {
                Resource.State.SUCCESS -> {
                    val data = it.data ?: emptyList()
                    tantanganAdapter.addItems(it.data!!)
                    progressDialog.dismiss()

                    if (!data.isEmpty()) {
                    }
                }
                Resource.State.ERROR -> {
                    Log.i("StoreAddressActivity", "getData: ${it.message}")
                    progressDialog.dismiss()
                }
                Resource.State.LOADING -> {
                    progressDialog.show()
                }
            }
        }
    }


}