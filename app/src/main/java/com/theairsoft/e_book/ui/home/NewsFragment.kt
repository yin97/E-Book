package com.theairsoft.e_book.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.theairsoft.e_book.MainActivity
import com.theairsoft.e_book.NEWS_ID
import com.theairsoft.e_book.R
import com.theairsoft.e_book.databinding.FragmentNewsBinding
import com.theairsoft.e_book.di.NewsViewModel
import com.theairsoft.e_book.showSnackbar


class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(layoutInflater, container, false)


        val id = arguments?.getLong(NEWS_ID)


        if (id != null) {
            viewModel.getNews(this, id)
        } else {
            showSnackbar("News not found")
        }


        viewModel.new.observe(viewLifecycleOwner) {
            binding.apply {
                Glide.with(requireContext())
                    .load(it.image)
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .into(ivNews)

                tvData.text = it.displayDate
                title.text = it.title
                subTitle.text = it.subTitle
            }
        }

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}