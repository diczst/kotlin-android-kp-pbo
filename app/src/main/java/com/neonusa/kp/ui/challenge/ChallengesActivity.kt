package com.neonusa.kp.ui.challenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.neonusa.kp.adapter.TantanganAdapter
import com.neonusa.kp.data.model.Tantangan
import com.neonusa.kp.data.network.Resource
import com.neonusa.kp.databinding.ActivityChallengesBinding
import com.techiness.progressdialoglibrary.ProgressDialog

class ChallengesActivity : AppCompatActivity() {
    companion object {
        const val MATERI_ID = "MATERI_ID"
        const val MATERI_LEVEL = "MATERI_LEVEL"
    }

    private lateinit var binding: ActivityChallengesBinding
    private lateinit var viewModel: ChallengeViewModel
    private lateinit var progressDialog: ProgressDialog

    private val tantanganAdapter = TantanganAdapter()

    var id = 0
    var level = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChallengesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getIntExtra(MATERI_ID,0)
        level = intent.getIntExtra(MATERI_LEVEL,0)

        viewModel = ViewModelProvider(this)[ChallengeViewModel::class.java]
        progressDialog = ProgressDialog(this)

        tantanganAdapter.setLevel(level)
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