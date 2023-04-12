package com.neonusa.kp.ui.leaderboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.neonusa.kp.adapter.LeaderboardAdapter
import com.neonusa.kp.data.network.Resource.State
import com.neonusa.kp.databinding.FragmentLeaderboardBinding
import com.techiness.progressdialoglibrary.ProgressDialog

class LeaderboardFragment : Fragment() {
    private var _binding: FragmentLeaderboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LeaderboardViewModel
    private val leaderboardAdapter = LeaderboardAdapter()
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(requireContext())
        viewModel = ViewModelProvider(this)[LeaderboardViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rv.adapter = leaderboardAdapter
        binding.rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL ,false)

        getData()

        return root
    }

    private fun getData() {
        viewModel.getUsers().observe(requireActivity()) {
            when (it.state) {
                State.SUCCESS -> {
                    val data = it.data ?: emptyList()
                    // tinggal masukin data ke adapternya
                    leaderboardAdapter.addItems(data)
                    leaderboardAdapter.getCurrentPos(data)
                    var pos = leaderboardAdapter.currentPosition
                    binding.tvCurrentPos.text = "Kamu berada di peringkat $pos"
                    progressDialog.dismiss()
                    if (data.isEmpty()) {
//                        binding.tvError.toVisible()
                    }
                }
                State.ERROR -> {
                    Log.i("StoreAddressActivity", "getData: ${it.message}")
                    progressDialog.dismiss()
                }
                State.LOADING -> {
                    progressDialog.show()
                }
            }
        }
    }


}