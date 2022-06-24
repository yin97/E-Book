package com.theairsoft.e_book.ui.home

import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.theairsoft.e_book.R
import com.theairsoft.e_book.databinding.ItemBookBinding

class BookData(
    val id: Int,
    val name: String? = null,
    val author: String? = null,
    val image: Int? = null
) : AbstractItem<BookData.BookViewHolder>() {

    override var identifier: Long
        get() = id.toLong()
        set(value) {}

    inner class BookViewHolder(itemView: View) : FastAdapter.ViewHolder<BookData>(itemView) {
        override fun bindView(item: BookData, payloads: List<Any>) {
            val binding = ItemBookBinding.bind(itemView)

            binding.ivBook.setImageDrawable(item.image?.let {
                ResourcesCompat.getDrawable(
                    itemView.resources,
                    it,
                    itemView.context.theme
                )
            })
            binding.tvBookName.text = item.name
            binding.tvBookAuthor.text = item.author
            binding.ratingBar.rating = 4f
        }

        override fun unbindView(item: BookData) {

        }

    }

    override val type: Int
        get() = 0
    override val layoutRes: Int
        get() = R.layout.item_book

    override fun getViewHolder(v: View): BookViewHolder = BookViewHolder(v)
}