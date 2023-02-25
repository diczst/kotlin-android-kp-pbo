package com.neonusa.kp.ui.detailmateri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.neonusa.kp.Kotpreference
import com.neonusa.kp.data.network.Resource
import com.neonusa.kp.data.request.SelesaiMateriRequest
import com.neonusa.kp.databinding.ActivityDetailMateriBinding
import com.techiness.progressdialoglibrary.ProgressDialog

class DetailMateriActivity : AppCompatActivity() {
    companion object{
        const val MATERI_ID = "MATERI_ID"
    }

    private var materiId: Int = 0

    private val BASE_URL = "http://192.168.43.181/pebeo/public" // ini kalau pakai hotspot euler
//    private val BASE_URL = "http://10.102.14.17/pebeo/public" // ini kalau pakai wifi unib

    private val USER_URL = "$BASE_URL/storage/user/"

    private lateinit var binding: ActivityDetailMateriBinding

    private lateinit var viewModel: DetailMateriViewModel
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMateriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        materiId = intent.getIntExtra(DetailMateriActivity.MATERI_ID,0)
        progressDialog = ProgressDialog(this)
        viewModel = ViewModelProvider(this)[DetailMateriViewModel::class.java]

        binding.btnSudah.setOnClickListener {
            MaterialDialog(this).show {
                title(text = "Sudah Dibaca")
                message(text = "Tandai sebagai sudah dibaca? Kamu akan mendapatkan +10exp")
                negativeButton(text = "Tidak")
                positiveButton(text = "Ya") {
                    update()
                }

            }
        }

        getMateriDetail()

        // baru bisa ditekan setelah minimal 1 menit
        Handler().postDelayed({
            binding.btnSudah.isEnabled = true

        },3000)
    }

    private fun getMateriDetail(){
        viewModel.getDataMateri(materiId.toString()).observe(this){
            when(it.state){
                Resource.State.SUCCESS -> {
                    val data = it.data
                    progressDialog.dismiss()
                    binding.tvNama.text = data?.nama.toString()
                    binding.tvKonten.text = data?.konten.toString()
//                    if(data?.image != null){
//                        Picasso.get().load(USER_URL + data.image).into(binding.imgProfile)
//                    }
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

    //todo: kasih minimal waktu baca materi misalnya 3 menit

    private fun update() {
        val userId = Kotpreference.getUser()?.id
        val currentExp = Kotpreference.getUser()?.exp?.toInt()
        val totalExp = currentExp?.plus(10)

        val body = SelesaiMateriRequest(
            userId ?: 0,
            totalExp.toString()
        )

        viewModel.selesaiMateri(body).observe(this) {
            when (it.state) {
                Resource.State.SUCCESS -> {
                    finish()
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