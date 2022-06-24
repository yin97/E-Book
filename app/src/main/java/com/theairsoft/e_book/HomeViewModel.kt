package com.theairsoft.e_book

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theairsoft.e_book.ui.home.BookData

class HomeViewModel : ViewModel() {

    val books = MutableLiveData<ArrayList<BookData>>(arrayListOf())
    val recently = MutableLiveData<ArrayList<BookData>>()

    fun addBooks() {
        val list = ArrayList<BookData>()
        list.add(BookData(0, "Fatherhood", "Marcus Berkmann", R.drawable.fatherhood))
        list.add(BookData(1, "The Zoo", "by Christopher Wilson", R.drawable.the_zoo))
        list.add(
            BookData(
                2,
                "In A Land Of Paper Gods",
                "by Rebecca Mackenzie",
                R.drawable.in_a_land_of_paper_gods
            )
        )
        list.add(BookData(3, "Tattletale", "by Sarah J. Noughton", R.drawable.tattletale))
        list.add(BookData(4, "The Fatal Tree", "by Jake Arnott", R.drawable.the_fatal_tree))
        list.add(BookData(5, "Day Four", "by LOTZ, SARAH", R.drawable.day_four))
        list.add(BookData(6, "Door to Door", "by Edward Humes", R.drawable.d2d))

        books.postValue(list)
    }

    fun addRecentlyBooks() {
        val list = ArrayList<BookData>()
        list.add(BookData(0, "The Fatal Tree", "by Jake Arnott", R.drawable.the_fatal_tree))
        list.add(BookData(1, "Day Four", "by LOTZ, SARAH", R.drawable.day_four))
        list.add(BookData(2, "Door to Door", "by Edward Humes", R.drawable.d2d))

        recently.postValue(list)
    }


}