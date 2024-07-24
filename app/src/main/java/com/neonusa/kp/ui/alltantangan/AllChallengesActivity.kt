package com.neonusa.kp.ui.alltantangan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.neonusa.kp.Kotpreference
import com.neonusa.kp.R
import com.neonusa.kp.adapter.MateriAdapter
import com.neonusa.kp.data.network.Resource
import com.neonusa.kp.databinding.ActivityAllChallengesBinding
import com.neonusa.kp.databinding.ActivityAllMateriBinding
import com.neonusa.kp.ui.home.HomeViewModel
import com.techiness.progressdialoglibrary.ProgressDialog

class AllChallengesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllChallengesBinding

    private lateinit var viewModel: HomeViewModel
    private lateinit var progressDialog: ProgressDialog

    private val tantanganAdapter = MateriAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllChallengesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        progressDialog = ProgressDialog(this)

        binding.rvMateri.adapter = tantanganAdapter

        val user = Kotpreference.getUser()
        tantanganAdapter.userMateriLevel = user?.level_materi!!

        getMateriDanTantangan()
    }

    private fun getMateriDanTantangan() {
        viewModel.getDataMateri().observe(this) {
            when (it.state) {
                Resource.State.SUCCESS -> {
                    val data = it.data ?: emptyList()
                    tantanganAdapter.type = "tantangan"
                    tantanganAdapter.addItems(data)
                    progressDialog.dismiss()
                }
                Resource.State.ERROR -> {
                    progressDialog.dismiss()
                }
                Resource.State.LOADING -> {
                    progressDialog.show()
                }
            }
        }
    }

}