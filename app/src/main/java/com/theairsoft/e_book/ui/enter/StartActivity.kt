package com.theairsoft.e_book.ui.enter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.theairsoft.e_book.changeColorStatusBar
import com.theairsoft.e_book.database.UserDatabase
import com.theairsoft.e_book.database.UserRepository
import com.theairsoft.e_book.database.UserViewModel
import com.theairsoft.e_book.database.UserViewModelFactory
import com.theairsoft.e_book.databinding.ActivityStartBinding
import dagger.hilt.android.AndroidEntryPoint

const val NUM_PAGES = 4
@AndroidEntryPoint
class StartActivity : AppCompatActivity() {

    private var _binding: ActivityStartBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: UserViewModel
    lateinit var noteDatabase: UserDatabase
    lateinit var repository: UserRepository
    lateinit var factory: UserViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeColorStatusBar(true)

        noteDatabase = UserDatabase(this)
        repository = UserRepository(noteDatabase)
        factory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]
    }

}

