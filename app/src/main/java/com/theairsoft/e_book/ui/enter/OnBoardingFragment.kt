package com.theairsoft.e_book.ui.enter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.theairsoft.e_book.ScreenSlidePagerAdapter
import com.theairsoft.e_book.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : Fragment() {
    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        val pagerAdapter = ScreenSlidePagerAdapter(requireActivity())
        binding.fragmentPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.fragmentPager) { tab, position -> }.attach()

        return binding.root
    }

}