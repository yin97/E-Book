package com.theairsoft.e_book.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.theairsoft.e_book.*
import com.theairsoft.e_book.databinding.FragmentHomeBinding
import com.theairsoft.e_book.di.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), OnBookItemClickListener, OnNewsItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val itemAdapter = ItemAdapter<BookData>()
    private val fastItemAdapter = FastAdapter.with(itemAdapter)

    private val itemAdapterNews = ItemAdapter<NewsItem>()
    private val fastItemAdapterNews = FastAdapter.with(itemAdapterNews)

    private val viewModel: NewsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apply {
            getBooks(this@HomeFragment)
            getNews(this@HomeFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.btnMyBook.setOnClickListener {
            startActivity(Intent(requireContext(), ShowPdfActivity::class.java))
        }

        binding.listBestseller.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        fastItemAdapter.addEventHook(BookData.BookItemClickEvent(this))
        binding.listBestseller.adapter = fastItemAdapter

        val scale = resources.displayMetrics.density
        val marginPixels = (16 * scale + 0.5f).toInt()
        binding.listNews.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.listNews.itemAnimator = DefaultItemAnimator()
        fastItemAdapterNews.addEventHook(NewsItem.OnNewsItemClickEvent(this))
        binding.listNews.adapter = fastItemAdapterNews
        binding.listNews.addItemDecoration(SpacesItemDecoration(marginPixels, true, 2))

        viewModel.books.observe(viewLifecycleOwner) { books ->
            itemAdapter.clear()
            itemAdapter.add(books)
        }

        viewModel.news.observe(viewLifecycleOwner) { news ->
            itemAdapterNews.clear()
            itemAdapterNews.add(news)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickItem(item: BookData) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
        startActivity(browserIntent)
    }

    override fun onClick(item: NewsItem) {
        val bundle = Bundle()
        item.id?.let { bundle.putLong(NEWS_ID, it) }
        findNavController().navigate(R.id.from_home_to_news_navigate, bundle)
    }
}