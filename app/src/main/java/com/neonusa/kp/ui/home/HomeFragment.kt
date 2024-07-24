package com.neonusa.kp.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.neonusa.kp.Kotpreference
import com.neonusa.kp.adapter.MateriAdapter
import com.neonusa.kp.data.network.Resource
import com.neonusa.kp.databinding.FragmentHomeBinding
import com.neonusa.kp.ui.allmateri.AllMateriActivity
import com.neonusa.kp.ui.alltantangan.AllChallengesActivity
import com.neonusa.kp.ui.challenge.ChallengesActivity
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

//        viewModel.getDataUser(Kotpreference.getUser()?.id.toString()).observe(requireActivity()){
//            when(it.state){
//                Resource.State.SUCCESS -> {
//                    val data = it.data
//                    materiAdapter.userMateriLevel = data?.level_materi!!
//
//                    tantanganAdapter.userMateriLevel = data.level_materi!!
//                    tantanganAdapter.userTantanganLevel = data.level_tantangan!!
//                }
//
//                Resource.State.ERROR -> {
//                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
//                }
//                Resource.State.LOADING -> {
//                }
//            }
//        }

        binding.tvSeeAll.setOnClickListener {
            val intent = Intent(root.context, AllMateriActivity::class.java)
            root.context.startActivity(intent)
        }

        binding.tvSeeAllChallenges.setOnClickListener {
            val intent = Intent(root.context, AllChallengesActivity::class.java)
            root.context.startActivity(intent)
        }

        getInformasiUser()
        getMateriDanTantangan()
        return root
    }

    fun getInformasiUser(){
        val user = Kotpreference.getUser()

        materiAdapter.userMateriLevel = user?.level_materi!!
        materiAdapter.userTantanganLevel = user.level_tantangan!!

        tantanganAdapter.userMateriLevel = user.level_materi!!
        tantanganAdapter.userTantanganLevel = user.level_tantangan!!

        binding.layoutHomeMember.tvExp.text =  user?.exp.toString()
        binding.layoutHomeMember.tvName.text =  user?.nama_lengkap.toString()

        if(user.exp!! < 300){
            binding.layoutHomeMember.tvRank.text = "Perunggu"
        }

        if(user.exp in 300..700){
            binding.layoutHomeMember.tvRank.text = "Perak"
        }

        if(user.exp in 701..1000){
            binding.layoutHomeMember.tvRank.text = "Emas"
        }


    }

//    fun getInformasiUser(){
//        viewModel.getDataUser(Kotpreference.getUser()?.id.toString()).observe(requireActivity()){
//            when(it.state){
//                Resource.State.SUCCESS -> {
//                    val data = it.data
//                    binding.layoutHomeMember.tvExp.text = data?.exp.toString()
//                    binding.layoutHomeMember.tvName.text = data?.nama_lengkap.toString()
//
//                    if(data?.exp!! < 300){
//                        binding.layoutHomeMember.tvRank.text = "Perunggu"
//                    }
//
//                    if(data.exp in 300..700){
//                        binding.layoutHomeMember.tvRank.text = "Perak"
//                    }
//
//                    if(data.exp in 701..1000){
//                        binding.layoutHomeMember.tvRank.text = "Emas"
//                    }
//                }
//
//                Resource.State.ERROR -> {
//                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
//                }
//                Resource.State.LOADING -> {
////                    progressDialog.show()
//                }
//            }
//        }
//    }

    private fun getMateriDanTantangan() {
        viewModel.getDataMateri().observe(requireActivity()) {
            when (it.state) {
                Resource.State.SUCCESS -> {
                    //todo: tambahin halaman lihat semua tantangan
                    val data = it.data?.take(9) ?: emptyList()

                    tantanganAdapter.addItems(data)
                    tantanganAdapter.type = "tantangan"
                    materiAdapter.addItems(data)
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