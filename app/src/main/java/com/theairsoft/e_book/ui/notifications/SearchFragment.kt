package com.theairsoft.e_book.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.listeners.ClickEventHook
import com.theairsoft.e_book.HomeViewModel
import com.theairsoft.e_book.PdfReaderActivity
import com.theairsoft.e_book.SpacesItemDecoration
import com.theairsoft.e_book.databinding.FragmentNotificationsBinding
import com.theairsoft.e_book.di.NewsViewModel
import com.theairsoft.e_book.ui.home.BookData
import com.theairsoft.e_book.ui.home.NewsItem
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private val itemAdapter = ItemAdapter<BookData>()
    private val fastItemAdapter =
        FastAdapter.with(itemAdapter).addEventHook(object : ClickEventHook<BookData>() {
            override fun onClick(
                v: View,
                position: Int,
                fastAdapter: FastAdapter<BookData>,
                item: BookData
            ) {
                val intent = Intent(requireContext(), PdfReaderActivity::class.java)
                startActivity(intent)
            }

            override fun onBind(viewHolder: RecyclerView.ViewHolder): View? {
                return viewHolder.itemView
            }

        })
    private val viewModel:NewsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.listSearch.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.listSearch.itemAnimator = DefaultItemAnimator()
        binding.listSearch.adapter = fastItemAdapter

        val scale = resources.displayMetrics.density
        val marginPixels = (16 * scale + 0.5f).toInt()
        binding.listSearch.addItemDecoration(SpacesItemDecoration(marginPixels, true, 2))

        binding.etSearch.addTextChangedListener {
            if (it != null) {
                if (it.toString().isNotEmpty()) {
                    itemAdapter.clear()
                    itemAdapter.add((viewModel.books.value as ArrayList<BookData>).filter { e ->
                        e.name?.lowercase(Locale.getDefault())?.contains(
                            it.toString()
                        ) ?: e.author?.lowercase(Locale.getDefault())?.contains(it.toString())
                        ?: false
                    })
                } else {
                    itemAdapter.clear()
                }
            } else {
                itemAdapter.clear()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}