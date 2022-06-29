package com.theairsoft.e_book.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.mikepenz.fastadapter.listeners.ClickEventHook
import com.theairsoft.e_book.DateHelper
import com.theairsoft.e_book.R
import com.theairsoft.e_book.databinding.ItemNewsBinding
import com.theairsoft.e_book.toDisplayString
import java.util.*
import kotlin.random.Random

class NewsItem(
    val id: Long?,
    val publishedDate: String?,
    val title: String?,
    val subTitle: String?,
    val image: String? = null
) : AbstractItem<NewsItem.NewsViewHolder>() {

    override var identifier: Long
        get() = id ?: Random.nextLong()
        set(value) {}

    val messageDate: Date
        get() = publishedDate?.let { DateHelper.str2Date(it, "yyyy-MM-dd") } ?: Date()

    val displayDate: String
        get() {
            return messageDate.toDisplayString()
        }

    inner class NewsViewHolder(itemView: View) : FastAdapter.ViewHolder<NewsItem>(itemView) {
        override fun bindView(item: NewsItem, payloads: List<Any>) {
            val binding = ItemNewsBinding.bind(itemView)
            binding.apply {

                Glide.with(itemView.context)
                    .load(item.image)
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .into(ivNews)

                title.text = item.title
                subTitle.text = item.subTitle
                tvData.text = displayDate
            }

        }

        override fun unbindView(item: NewsItem) {

        }

    }

    override val type: Int
        get() = 0
    override val layoutRes: Int
        get() = R.layout.item_news

    override fun getViewHolder(v: View): NewsViewHolder = NewsViewHolder(v)

     class OnNewsItemClickEvent(private val listener: OnNewsItemClickListener) :
        ClickEventHook<NewsItem>() {
        override fun onClick(
            v: View,
            position: Int,
            fastAdapter: FastAdapter<NewsItem>,
            item: NewsItem
        ) {
            listener.onClick(item)
        }

        override fun onBind(viewHolder: RecyclerView.ViewHolder): View? {
            return if (viewHolder is NewsItem.NewsViewHolder) {
                viewHolder.itemView
            } else {
                super.onBind(viewHolder)
            }
        }
    }
}

interface OnNewsItemClickListener {
    fun onClick(item: NewsItem)
}