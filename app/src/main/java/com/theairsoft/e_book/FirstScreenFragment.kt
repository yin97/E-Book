package com.theairsoft.e_book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.theairsoft.e_book.databinding.FragmentFirstScreenBinding

class FirstScreenFragment(
    private val position: Int
) : Fragment() {
    private var _binding: FragmentFirstScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstScreenBinding.inflate(inflater, container, false)

        setupViews(position)

        return binding.root
    }

    private fun setupViews(position: Int) {
        when (position) {
            0 -> {
                binding.apply {
                    tvTitle.text = "Discounted\nSecondhand Books"
                    tvSubTitle.text = "Used and near new secondhand books at great \nprices."
                    ivImage.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.image1,
                            requireActivity().theme
                        )
                    )
                }
            }
            1 -> {
                binding.apply {
                    tvTitle.text = "20 Book Grocers \nNationally"
                    tvSubTitle.text = "We've successfully opened 20 stores across \nAustralia."
                    ivImage.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.image2,
                            requireActivity().theme
                        )
                    )
                }
            }
            2 -> {
                binding.apply {
                    tvTitle.text = "Sell or Recycle Your Old \nBooks With Us"
                    tvSubTitle.text =
                        "If you're looking to downsize, sell or recycle old \nbooks, the Book Grocer can help."
                    ivImage.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.image3,
                            requireActivity().theme
                        )
                    )
                }
            }
            else -> {

            }
        }
    }
}