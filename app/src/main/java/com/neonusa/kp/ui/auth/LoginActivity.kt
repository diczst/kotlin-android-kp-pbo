package com.neonusa.kp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.neonusa.kp.MainActivity
import com.neonusa.kp.data.network.Resource.State
import com.neonusa.kp.data.request.LoginRequest
import com.neonusa.kp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(){
        if (binding.edtNisn.text!!.isEmpty()){
            binding.edtNisn.error = "NISN tidak boleh kosong"
            return
        }
        if (binding.edtPassword.text!!.isEmpty()) {
            binding.edtPassword.error = "Password tidak boleh kosong"
            return
        }

        val body = LoginRequest(
            binding.edtNisn.text.toString(),
            binding.edtPassword.text.toString()
        )

        viewModel.login(body).observe(this){
            when(it.state){
                State.SUCCESS -> {
//                    progressDialog.dismiss()
                    Toast.makeText(this, "Selamat datang : ${it.data?.nama}", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                State.ERROR -> {
//                    progressDialog.dismiss()
                    Toast.makeText(this, it.message ?: "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
                State.LOADING -> {
//                    progressDialog.show();
                }
            }
        }
    }
}