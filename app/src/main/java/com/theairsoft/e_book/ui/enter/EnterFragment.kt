package com.theairsoft.e_book.ui.enter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.theairsoft.e_book.R
import com.theairsoft.e_book.databinding.FragmentEnterBinding
import com.theairsoft.e_book.di.NewsViewModel
import com.theairsoft.e_book.di.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnterFragment : Fragment() {
    private var _binding: FragmentEnterBinding? = null
    private val binding get() = _binding!!

    private val vm: NewsViewModel by viewModels()

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

//        vm.news.observe(viewLifecycleOwner) {
//            when (it.status) {
//                Resource.Status.SUCCESS -> {
////                    progress_bar.visibility = View.GONE
//                    if (!it.data.isNullOrEmpty()) Log.d(
//                        "TAGRESULT",
//                        "onCreateView: ${it.data.size}"
//                    )
//                }
//                Resource.Status.ERROR -> {
//                    Log.d("TAGRESULT", "onCreateView: ${it.message}")
//                }
//
//                Resource.Status.LOADING -> {
////                    progress_bar.visibility = View.VISIBLE
//                }
//            }
//        }

        return binding.root
    }
}