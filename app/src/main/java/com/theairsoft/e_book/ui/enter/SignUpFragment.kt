package com.theairsoft.e_book.ui.enter

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.theairsoft.e_book.*
import com.theairsoft.e_book.database.UserEntity
import com.theairsoft.e_book.databinding.FragmentSignUpBinding
import com.theairsoft.e_book.di.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.math.absoluteValue
import kotlin.random.Random

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val viewModel:NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        binding.etPhone.setMaskOn(activity = activity)
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.etEmail.addTextChangedListener {
            if (validateSender()) {
                binding.btnSignUp.isEnabled = true
                binding.btnSignUp.setTextColor(Color.WHITE)
            }
        }

        binding.etFirst.addTextChangedListener {
            if (validateSender()) {
                binding.btnSignUp.isEnabled = true
                binding.btnSignUp.setTextColor(Color.WHITE)
            }
        }

        binding.etPhone.addTextChangedListener {
            if (validateSender()) {
                binding.btnSignUp.isEnabled = true
                binding.btnSignUp.setTextColor(Color.WHITE)
            }
        }

        binding.etPassword.addTextChangedListener {
            if (validateSender()) {
                binding.btnSignUp.isEnabled = true
                binding.btnSignUp.setTextColor(Color.WHITE)
            }
        }

        binding.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (validateSender()) {
                binding.btnSignUp.isEnabled = true
                binding.btnSignUp.setTextColor(Color.WHITE)
            }
        }

        binding.btnSignUp.setOnClickListener {
            insertUserData()
        }

        return binding.root
    }

    private fun insertUserData() {
        val email = binding.etEmail.text.toString()
        val phone = binding.etPhone.getMaskedPhoneWithoutSpace()
        val password = binding.etPassword.text.toString()
        val name = binding.etFirst.text.toString()

        val user = UserEntity(
            id = Random.nextInt().absoluteValue,
            name = name,
            email = email,
            mobilePhone = phone,
            password = password
        )


                viewModel.apply {
                    checkData(user, this@SignUpFragment)
                }

    }


    private fun validateSender(): Boolean {
        val errorMessage = "Fill in the field"
        if (binding.etEmail.text?.trim()?.length == 0) {
            binding.etEmail.error = errorMessage
            return false
        }
        if (binding.etPassword.text?.trim()?.length == 0) {
            binding.etPassword.error = errorMessage
            return false
        }
        if (binding.etFirst.text?.trim()?.length == 0) {
            binding.etFirst.error = errorMessage
            return false
        }
        if (binding.etPhone.text?.trim()?.length == 0) {
            binding.etPhone.error = errorMessage
            return false
        }
        if (!binding.checkbox.isChecked) {
            binding.checkbox.error = "Please, check this"
            return false
        }

        return true
    }

}