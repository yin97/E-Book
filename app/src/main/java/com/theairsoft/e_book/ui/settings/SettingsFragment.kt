package com.theairsoft.e_book.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.theairsoft.e_book.MainActivity
import com.theairsoft.e_book.SharedPrefs
import com.theairsoft.e_book.databinding.FragmentSettingsBinding
import com.theairsoft.e_book.di.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {


    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.llLogOut.setOnClickListener {
            SharedPrefs(requireContext()).setFirstTime(false)
            requireActivity().startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }

        SharedPrefs(requireContext()).userId.let { userId ->
            viewModel.getUserById(this, userId)
        }

        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.apply {
                tvName.text = user.name
                tvEmail.text = user.email
                tvNumber.text = "+998 ${user.mobilePhone}"
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}