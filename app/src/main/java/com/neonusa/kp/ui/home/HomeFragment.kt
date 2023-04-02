package com.neonusa.kp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.neonusa.kp.Kotpreference
import com.neonusa.kp.adapter.MateriAdapter
import com.neonusa.kp.data.network.Resource
import com.neonusa.kp.databinding.FragmentHomeBinding
import com.techiness.progressdialoglibrary.ProgressDialog

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel
    private lateinit var progressDialog: ProgressDialog

    private val materiAdapter = MateriAdapter()
    private val tantanganAdapter = MateriAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        progressDialog = ProgressDialog(requireActivity())
    }

    override fun onResume() {
        super.onResume()
        getInformasiUser()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rvMateri.adapter = materiAdapter
        binding.rvTantangan.adapter = tantanganAdapter

        getInformasiUser()
        getMateri()
        getTantangan()

        return root
    }

    fun getInformasiUser(){
        viewModel.getDataUser(Kotpreference.getUser()?.id.toString()).observe(requireActivity()){
            when(it.state){
                Resource.State.SUCCESS -> {
                    val data = it.data
                    progressDialog.dismiss()
                    binding.layoutHomeMember.tvCoin.text = data?.coin.toString()
                    binding.layoutHomeMember.tvExp.text = data?.exp.toString()
                    binding.layoutHomeMember.tvName.text = data?.nama_lengkap.toString()
                }

                Resource.State.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.State.LOADING -> {
//                    progressDialog.show()
                }
            }
        }
    }

    private fun getMateri() {
        viewModel.getDataMateri().observe(requireActivity()) {
            when (it.state) {
                Resource.State.SUCCESS -> {
                    val data = it.data ?: emptyList()
                    materiAdapter.addItems(data)
                    Log.i("HOMEFRAGMENT", "getMateri: $data")
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

    fun getTantangan(){
        viewModel.getDataMateri().observe(requireActivity()){
            when (it.state) {
                Resource.State.SUCCESS -> {
                    val data = it.data ?: emptyList()
                    tantanganAdapter.addItems(data)
                    tantanganAdapter.type = "tantangan"
                    progressDialog.dismiss()

                    //todo: materi business process
                    //todo: ^ masuk ke semua mata pelajaran

                    //todo : tambah level materi

                    //todo : tambah jenjang
                    //todo : smp, sma, smk

                    //todo : switch mata pelajaran
                    //todo : Pemrograman web perangkat bergerak (pwpb)
                    //todo : basis data
                    //todo : tambah varchar di materi

                    //todo : alamat, ttl (melengkapi biodata)

                    //todo : notifikasi jika tidak membuka app
                    //todo : mengingatkan untuk belajar materi terakhir

                    //todo : quis ambil soal ujian smk (selain yg pbo)

                    //todo: contoh kode program di bawah tantangan
                    //todo:  sudah membaca langsung ke kuis
                    //todo: hasil kuis tmbahkan tombol untuk ngarahkan ke
                    //      contoh kode program

                    //todo: tambahkan sudah dibaca pada contoh kode program

                    if (!data.isEmpty()) {
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