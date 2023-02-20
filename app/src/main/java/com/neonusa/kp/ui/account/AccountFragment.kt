package com.neonusa.kp.ui.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.chibatching.kotpref.Kotpref
import com.neonusa.kp.Kotpreference
import com.neonusa.kp.R
import com.neonusa.kp.databinding.FragmentAccountBinding
import com.neonusa.kp.databinding.FragmentHomeBinding
import com.neonusa.kp.ui.auth.LoginActivity
import com.neonusa.kp.ui.home.HomeViewModel


class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnLogout.setOnClickListener {
            MaterialDialog(requireContext()).show {
                title(text = "KELUAR AKUN")
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

        return root
    }



}