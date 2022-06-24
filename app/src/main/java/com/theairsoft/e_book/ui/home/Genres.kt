package com.theairsoft.e_book.ui.home

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.theairsoft.e_book.R
import com.theairsoft.e_book.databinding.ItemGenresBinding

class Genres(
    val id: Int,
    val image: Int?=null
) : AbstractItem<Genres.GenresViewHolder>() {

    override var identifier: Long
        get() = id.toLong()
        set(value) {}

    inner class GenresViewHolder(itemView: View) : FastAdapter.ViewHolder<Genres>(itemView) {
        override fun bindView(item: Genres, payloads: List<Any>) {
            val binding = ItemGenresBinding.bind(itemView)
        }

        override fun unbindView(item: Genres) {

        }

    }

    override val type: Int
        get() = 0
    override val layoutRes: Int
        get() = R.layout.item_genres

    override fun getViewHolder(v: View): GenresViewHolder = GenresViewHolder(v)
}