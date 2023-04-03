package com.neonusa.kp.ui.account

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.github.drjacky.imagepicker.ImagePicker
import com.neonusa.kp.Kotpreference
import com.neonusa.kp.MainActivity
import com.neonusa.kp.data.network.Resource.State
import com.neonusa.kp.data.request.UpdateUserRequest
import com.neonusa.kp.databinding.ActivityUpdateAccountBinding
import com.neonusa.kp.toMultipartBody
import com.squareup.picasso.Picasso
import com.techiness.progressdialoglibrary.ProgressDialog
import java.io.File

class UpdateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateAccountBinding
    private lateinit var viewModel: AccountViewModel
    private lateinit var progressDialog: ProgressDialog

    private var fileImage: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[AccountViewModel::class.java]
        progressDialog = ProgressDialog(this)

        binding.btnSave.setOnClickListener {
            if (fileImage != null) {
                uploadImageAndUpdate()
            } else {
                update()
            }
        }

        binding.img.setOnClickListener {
            pickImage()
        }

        setData()
    }

    private fun setData() {
        val user = Kotpreference.getUser()
        if (user != null) {
            binding.apply {
                edtName.setText(user.nama_lengkap)
                edtNisn.setText(user.nisn)
                edtPhone.setText(user.no_hp)

                edtMotto.setText(user.motto)
                edtAlamat.setText(user.alamat)
                edtTtl.setText(user.ttl)
            }
        }
    }


    private fun update() {
        if (binding.edtName.text!!.isEmpty()){
            binding.edtName.error = "Nama lengkap tidak boleh kosong"
            return
        }

        val userId = Kotpreference.getUser()?.id
        val body = UpdateUserRequest(
            userId ?: 0,
            binding.edtName.text.toString(),
            binding.edtMotto.text.toString(),
            binding.edtAlamat.text.toString()
        )

        viewModel.update(body).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    onBackPressed()
                }
                State.ERROR -> {
                    progressDialog.dismiss()
                }
                State.LOADING -> {
                    progressDialog.show()
                }
            }
        }
    }

    private fun pickImage() {
        ImagePicker.with(this)
            .crop()
            .maxResultSize(1080, 1080, true)
            .createIntentFromDialog { launcher.launch(it) }
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val uri = it.data?.data!!

            // konversi uri menjadi File
            fileImage = File(uri.path ?: "")

            // Use the uri to load the image
            Picasso.get().load(uri).into(binding.img)
        }
    }

    private fun uploadImageAndUpdate() {
        val idUser = Kotpreference.getUser()?.id
        val file = fileImage.toMultipartBody()

        viewModel.uploadUser(idUser, file).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    update()
                }
                State.ERROR -> {
                    progressDialog.dismiss()
                }
                State.LOADING -> {
                    progressDialog.show()
                }
            }
        }
    }

}