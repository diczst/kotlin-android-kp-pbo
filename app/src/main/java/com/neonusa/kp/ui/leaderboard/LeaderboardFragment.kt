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
import com.neonusa.kp.databinding.FragmentLeaderboardBinding


class LeaderboardFragment : Fragment() {
    private lateinit var viewModel: LeaderboardViewModel
    private var _binding: FragmentLeaderboardBinding? = null
    private val binding get() = _binding!!

    private val leaderboardAdapter = LeaderboardAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this)[LeaderboardViewModel::class.java]
        _binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rv.adapter = leaderboardAdapter
        binding.rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL ,false)

//        viewModel.listUsers.observe(requireActivity()) {
//            leaderboardAdapter.addItems(it)
//            Log.i("TAG", "onCreateView: $it")
//        }

        return root
    }


}