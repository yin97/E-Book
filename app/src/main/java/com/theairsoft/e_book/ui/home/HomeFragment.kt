package com.theairsoft.e_book.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.listeners.ClickEventHook
import com.theairsoft.e_book.*
import com.theairsoft.e_book.database.Offer
import com.theairsoft.e_book.databinding.FragmentHomeBinding
import com.theairsoft.e_book.di.NewsViewModel
import com.theairsoft.e_book.di.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
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
                return if (viewHolder is BookData.BookViewHolder) {
                    viewHolder.itemView
                } else {
                    super.onBind(viewHolder)
                }
            }

        })

    private val itemAdapterNews = ItemAdapter<NewsItem>()
    private val fastItemAdapterNews =
        FastAdapter.with(itemAdapterNews).addEventHook(object : ClickEventHook<NewsItem>() {
            override fun onClick(
                v: View,
                position: Int,
                fastAdapter: FastAdapter<NewsItem>,
                item: NewsItem
            ) {
                val bundle = Bundle()
                item.id?.let { bundle.putLong(NEWS_ID, it) }
                findNavController().navigate(R.id.from_home_to_news_navigate, bundle)
            }

            override fun onBind(viewHolder: RecyclerView.ViewHolder): View? {
                return if (viewHolder is NewsItem.NewsViewHolder) {
                    viewHolder.itemView
                } else {
                    super.onBind(viewHolder)
                }
            }

        })

    private val viewModel: NewsViewModel by activityViewModels()


    private val itemOfferAdapter = ItemAdapter<Offer>()
    private val fastItemOfferAdapter =
        FastAdapter.with(itemOfferAdapter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getBooks(this@HomeFragment)
        viewModel.getNews(this@HomeFragment)

        viewModel.getOffers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.listBestseller.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.listBestseller.adapter = fastItemAdapter

        val scale = resources.displayMetrics.density
        val marginPixels = (16 * scale + 0.5f).toInt()
        binding.listNews.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            itemAnimator = DefaultItemAnimator()
            adapter = fastItemAdapterNews
            addItemDecoration(SpacesItemDecoration(marginPixels, true, 2))
        }

        binding.listOffer.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            itemAnimator = DefaultItemAnimator()
            adapter = fastItemOfferAdapter
            addItemDecoration(SpacesItemDecoration(marginPixels, true, 2))
        }

        viewModel.books.observe(viewLifecycleOwner) { books ->
            itemAdapter.clear()
            itemAdapter.add(books)
        }

        viewModel.news.observe(viewLifecycleOwner) { news ->
            itemAdapterNews.clear()
            itemAdapterNews.add(news)
        }

        viewModel.offers.observe(viewLifecycleOwner) {
            itemOfferAdapter.clear()
            if (it != null) {
                itemOfferAdapter.add(it)
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}