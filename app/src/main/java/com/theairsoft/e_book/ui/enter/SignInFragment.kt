package com.theairsoft.e_book.ui.enter

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.theairsoft.e_book.MainActivity
import com.theairsoft.e_book.databinding.FragmentSignInBinding
import com.theairsoft.e_book.getMaskedPhoneWithoutSpace
import com.theairsoft.e_book.setMaskOn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.bind


class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        binding.etPhone.setMaskOn(activity = activity)
        binding.etPassword.addTextChangedListener {
            if (validateSender("Fill in the field")) {
                binding.btnSignIn.isEnabled = true
                binding.btnSignIn.setTextColor(Color.WHITE)
            }
        }

        binding.etPhone.addTextChangedListener {
            if (validateSender("Fill in the field")) {
                binding.btnSignIn.isEnabled = true
                binding.btnSignIn.setTextColor(Color.WHITE)
            }
        }

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSignIn.setOnClickListener {
            checkUserData()
        }

        return binding.root
    }

    private fun checkUserData() {
        val phone = binding.etPhone.getMaskedPhoneWithoutSpace()
        val password = binding.etPassword.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            (requireActivity() as StartActivity).let {
                it.viewModel.login(phone,password,this@SignInFragment)
            }
        }
    }

    private fun validateSender(
        errorMessage: String,
    ): Boolean {
        if (binding.etPhone.text?.trim()?.length == 0) {
            binding.etPhone.error = errorMessage
            return false
        }
        if (binding.etPassword.text?.trim()?.length == 0) {
            binding.etPassword.error = errorMessage
            return false
        }

        return true
    }

}
