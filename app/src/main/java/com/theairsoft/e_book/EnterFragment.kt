package com.theairsoft.e_book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.theairsoft.e_book.databinding.FragmentEnterBinding

class EnterFragment : Fragment() {
    private var _binding: FragmentEnterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterBinding.inflate(inflater, container, false)
        binding.btnSignIn.setOnClickListener {
            findNavController().navigate(R.id.onboard_to_sign_in)
        }
        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.onboard_to_sign_up)
        }
        return binding.root
    }
}