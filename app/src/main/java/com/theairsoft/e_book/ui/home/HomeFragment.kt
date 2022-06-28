package com.theairsoft.e_book.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.listeners.ClickEventHook
import com.theairsoft.e_book.HomeViewModel
import com.theairsoft.e_book.PdfReaderActivity
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
                return viewHolder.itemView
            }

        })

    private val itemAdapterRecently = ItemAdapter<BookData>()
    private val fastItemAdapterRecently =
        FastAdapter.with(itemAdapterRecently).addEventHook(object : ClickEventHook<BookData>() {
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

    private val itemAdapterGenres = ItemAdapter<Genres>()
    private val fastItemAdapterGenres = FastAdapter.with(itemAdapterGenres)
    private val vm: HomeViewModel by activityViewModels()
    private val viewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        vm.addBooks()
        vm.addRecentlyBooks()

        binding.listBestseller.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.listBestseller.adapter = fastItemAdapter

        vm.books.observe(viewLifecycleOwner) {
            itemAdapter.clear()
            itemAdapter.add(it)
        }

        binding.listGenres.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.listGenres.adapter = fastItemAdapterGenres

        binding.listRecently.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.listRecently.adapter = fastItemAdapterRecently

        viewModel.books.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
//                    progress_bar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) {
                        val list = ArrayList(it.data)
                        val books = ArrayList<BookData>()
                        list.let {
                            it.forEach { e ->
                                books.add(e.toBookData())
                            }
                        }
                        itemAdapter.clear()
                        itemAdapter.add(books)
                    }
                }
                Resource.Status.ERROR -> {
                    Log.d("TAGRESULT", "onCreateView: ${it.message}")
                }

                Resource.Status.LOADING -> {
//                    progress_bar.visibility = View.VISIBLE
                }
            }
        }

        itemAdapterGenres.clear()
        itemAdapterGenres.add(Genres(0))

        vm.recently.observe(viewLifecycleOwner) {
            itemAdapterRecently.clear()
            itemAdapterRecently.add(it)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}