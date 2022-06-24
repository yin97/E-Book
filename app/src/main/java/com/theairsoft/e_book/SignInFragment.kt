package com.theairsoft.e_book

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.theairsoft.e_book.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding.etCode.addTextChangedListener {
            if (validateSender("Fill in the field")) {
                binding.btnSignIn.isEnabled = true
                binding.btnSignIn.setTextColor(Color.WHITE)
            }
        }

        binding.etPassword.addTextChangedListener {
            if (validateSender("Fill in the field")) {
                binding.btnSignIn.isEnabled = true
                binding.btnSignIn.setTextColor(Color.WHITE)
            }
        }

        binding.etEmail.addTextChangedListener {
            if (validateSender("Fill in the field")) {
                binding.btnSignIn.isEnabled = true
                binding.btnSignIn.setTextColor(Color.WHITE)
            }
        }

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSignIn.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun validateSender(
        errorMessage: String,
    ): Boolean {
        if (binding.etCode.text?.trim()?.length == 0) {
            binding.etCode.error = errorMessage
            return false
        }
        if (binding.etEmail.text?.trim()?.length == 0) {
            binding.etEmail.error = errorMessage
            return false
        }
        if (binding.etPassword.text?.trim()?.length == 0) {
            binding.etPassword.error = errorMessage
            return false
        }

        return true
    }

}
