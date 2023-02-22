package com.neonusa.kp.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.neonusa.kp.MainActivity
import com.neonusa.kp.data.network.Resource.State
import com.neonusa.kp.databinding.ActivityRegisterBinding
import com.techiness.progressdialoglibrary.ProgressDialog
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        progressDialog = ProgressDialog(this)

        binding.btnRegister.setOnClickListener{
            register()
        }

    }

    private fun register(){
        if (binding.edtName.text!!.isEmpty()){
            binding.edtName.error = "Nama tidak boleh kosong"
            binding.edtName.requestFocus()
            return
        }

        if (binding.edtNisn.text!!.isEmpty()){
            binding.edtNisn.error = "NISN tidak boleh kosong"
            binding.edtNisn.requestFocus()
            return
        }

        if (binding.edtPhone.text!!.isEmpty()){
            binding.edtPhone.error = "No. HP tidak boleh kosong"
            binding.edtPhone.requestFocus()
            return
        }

        if (binding.edtPassword.text!!.isEmpty()){
            binding.edtPassword.error = "Password tidak boleh kosong"
            binding.edtPassword.requestFocus()
            return
        }

        val nisn = binding.edtNisn.text.toString().toRequestBody("text/plain".toMediaType())
        val nama = binding.edtName.text.toString().toRequestBody("text/plain".toMediaType())
        val hp = binding.edtPhone.text.toString().toRequestBody("text/plain".toMediaType())
        val password = binding.edtPassword.text.toString().toRequestBody("text/plain".toMediaType())

        viewModel.register(nisn, nama, hp, password).observe(this) {
            when(it.state){
                State.SUCCESS -> {
                    progressDialog.dismiss()
                    Toast.makeText(this, "Berhasil Mendaftar Akun, Selamat datang : ${it.data?.data?.nama_lengkap}", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                State.ERROR -> {
                    progressDialog.dismiss()
                    Toast.makeText(this, it.message ?: "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
                State.LOADING -> {
                    progressDialog.show();
                }
            }

        }
    }
}