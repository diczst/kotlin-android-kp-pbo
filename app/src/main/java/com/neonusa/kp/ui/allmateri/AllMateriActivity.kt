package com.neonusa.kp.ui.allmateri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.neonusa.kp.Kotpreference
import com.neonusa.kp.R
import com.neonusa.kp.adapter.MateriAdapter
import com.neonusa.kp.data.network.Resource
import com.neonusa.kp.databinding.ActivityAllMateriBinding
import com.neonusa.kp.databinding.FragmentHomeBinding
import com.neonusa.kp.ui.home.HomeViewModel
import com.techiness.progressdialoglibrary.ProgressDialog

class AllMateriActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllMateriBinding

    private lateinit var viewModel: HomeViewModel
    private lateinit var progressDialog: ProgressDialog

    private val materiAdapter = MateriAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllMateriBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        progressDialog = ProgressDialog(this)


        binding.rvMateri.adapter = materiAdapter

        val user = Kotpreference.getUser()
        materiAdapter.userMateriLevel = user?.level_materi!!

        getMateriDanTantangan()
    }

    private fun getMateriDanTantangan() {
        viewModel.getDataMateri().observe(this) {
            when (it.state) {
                Resource.State.SUCCESS -> {
                    val data = it.data ?: emptyList()
                    materiAdapter.addItems(data)
                    progressDialog.dismiss()
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