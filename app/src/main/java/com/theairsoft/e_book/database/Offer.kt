package com.theairsoft.e_book.database

import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.theairsoft.e_book.R
import com.theairsoft.e_book.databinding.ItemOfferBinding
import kotlin.random.Random

class Offer(
    val id: Int? = null,
    val name: String? = null,
    val brand: String? = null,
    val category: String? = null,
    val merchant: String? = null,
    val attributes: List<Attribute>,
    val image: ImageData
) : AbstractItem<Offer.OfferViewHolder>() {
    override var identifier: Long
        get() = id?.toLong() ?: Random.nextLong()
        set(value) {}

    inner class OfferViewHolder(itemView: View) : FastAdapter.ViewHolder<Offer>(itemView) {
        override fun bindView(item: Offer, payloads: List<Any>) {
            val binding = ItemOfferBinding.bind(itemView)

            Glide.with(itemView.context)
                .load(item.image.url)
                .placeholder(
                    ResourcesCompat.getDrawable(
                        itemView.resources,
                        R.drawable.baseline_downloading_24,
                        itemView.context.theme
                    )
                )
                .error(
                    ResourcesCompat.getDrawable(
                        itemView.resources,
                        R.drawable.baseline_image_not_supported_24,
                        itemView.context.theme
                    )
                )
                .into(binding.ivOffer)

            binding.tvBrandName.text = item.brand
            binding.tvName.text = item.name
            binding.tvCategoryName.text = item.category

        }

        override fun unbindView(item: Offer) {

        }

    }

    override val layoutRes: Int
        get() = R.layout.item_offer
    override val type: Int
        get() = 0

    override fun getViewHolder(v: View): OfferViewHolder = OfferViewHolder(v)
}

data class Attribute(
    val name: String? = null,
    val value: String? = null
)

data class ImageData(
    val width: Int? = null,
    val height: Int? = null,
    val url: String? = null
)