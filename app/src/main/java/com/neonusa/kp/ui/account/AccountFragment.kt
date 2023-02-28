package com.neonusa.kp.ui.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.neonusa.kp.Kotpreference
import com.neonusa.kp.data.network.Resource.State
import com.neonusa.kp.databinding.FragmentAccountBinding
import com.neonusa.kp.ui.login.LoginActivity
import com.squareup.picasso.Picasso
import com.techiness.progressdialoglibrary.ProgressDialog


class AccountFragment : Fragment() {
    private val BASE_URL = "http://192.168.43.181/pebeo/public" // ini kalau pakai hotspot euler
//    private val BASE_URL = "http://10.102.14.17/pebeo/public" // ini kalau pakai wifi unib

    private val USER_URL = "$BASE_URL/storage/user/"

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AccountViewModel
    private lateinit var progressDialog: ProgressDialog


    override fun onResume() {
        super.onResume()
        getInformasiUser()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[AccountViewModel::class.java]
        progressDialog = ProgressDialog(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnLogout.setOnClickListener {
            MaterialDialog(requireContext()).show {
                title(text = "Keluar Akun")
                message(text = "Apakah kamu yakin ingin keluar dari akun?")
                negativeButton(text = "Tidak")
                positiveButton(text = "Ya") {
                    Kotpreference.isLogin = false
                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                    Toast.makeText(requireActivity(), "Berhasil keluar akun", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.btnUpdate.setOnClickListener {
            val intent = Intent(requireActivity(), UpdateAccountActivity::class.java)
            startActivity(intent)
        }

        getInformasiUser()
        return root
    }

    fun getInformasiUser(){
        viewModel.getDataUser(Kotpreference.getUser()?.id.toString()).observe(requireActivity()){
            when(it.state){
                State.SUCCESS -> {
                    val data = it.data
                    progressDialog.dismiss()
                    binding.tvName.text = data?.nama_lengkap.toString()
                    binding.tvNisn.text = "NISN. ${data?.nisn}"
                    binding.tvMotto.text = data?.motto ?: "Motto belum ditambahkan"
                    binding.tvExp.text = data?.exp.toString()
                    Picasso.get().load(USER_URL + data?.image).into(binding.imgProfile)

                    if(data?.exp?.toInt()!! < 300){
                        binding.tvRank.text = "Perunggu"
                    }

                    if(data.exp in 300..700){
                        binding.tvRank.text = "Perak"
                    }

                    if(data.exp in 701..1000){
                        binding.tvRank.text = "Emas"
                    }

                }

                State.ERROR -> {
                }
                State.LOADING -> {
//                    progressDialog.show()
                }
            }
        }
    }

}