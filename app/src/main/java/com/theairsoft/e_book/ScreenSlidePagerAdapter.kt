package com.theairsoft.e_book

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.theairsoft.e_book.ui.enter.EnterFragment
import com.theairsoft.e_book.ui.enter.FirstScreenFragment
import com.theairsoft.e_book.ui.enter.NUM_PAGES


class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment = when (position) {
        3 -> EnterFragment()
        else -> FirstScreenFragment(position)
    }

}