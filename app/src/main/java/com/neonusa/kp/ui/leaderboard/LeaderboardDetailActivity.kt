package com.neonusa.kp.ui.leaderboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.neonusa.kp.Kotpreference
import com.neonusa.kp.data.network.Resource
import com.neonusa.kp.databinding.ActivityLeaderboardDetailBinding
import com.squareup.picasso.Picasso
import com.techiness.progressdialoglibrary.ProgressDialog

class LeaderboardDetailActivity : AppCompatActivity() {
    companion object{
        const val USER_ID = "USER_ID"
    }

    private var userId: Int = 0

    private val BASE_URL = "http://192.168.43.181/pebeo/public" // ini kalau pakai hotspot euler
//    private val BASE_URL = "http://10.102.14.17/pebeo/public" // ini kalau pakai wifi unib

    private val USER_URL = "$BASE_URL/storage/user/"

    private lateinit var binding: ActivityLeaderboardDetailBinding

    private lateinit var viewModel: LeaderboardViewModel
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderboardDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getIntExtra(USER_ID,0)
        progressDialog = ProgressDialog(this)
        viewModel = ViewModelProvider(this)[LeaderboardViewModel::class.java]

        getUser()
    }

    private fun getUser(){
        viewModel.getDataUser(userId.toString()).observe(this){
            when(it.state){
                Resource.State.SUCCESS -> {
                    val data = it.data
                    progressDialog.dismiss()
                    binding.tvName.text = data?.nama_lengkap.toString()
                    binding.tvNisn.text = "NISN. ${data?.nisn}"
                    binding.tvMotto.text = data?.motto ?: "Motto belum ditambahkan"
                    binding.tvExp.text = data?.exp.toString()

                    if(data?.image != null){
                        Picasso.get().load(USER_URL + data.image).into(binding.imgProfile)

                    }
                    if(data?.exp!! < 300){
                        binding.tvRank.text = "Perunggu"
                    }

                    if(data.exp in 300..700){
                        binding.tvRank.text = "Perak"
                    }

                    if(data.exp in 701..1000){
                        binding.tvRank.text = "Emas"
                    }
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