package com.theairsoft.e_book.ui.enter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.theairsoft.e_book.changeColorStatusBar
import com.theairsoft.e_book.databinding.ActivityStartBinding
import dagger.hilt.android.AndroidEntryPoint

const val NUM_PAGES = 4

@AndroidEntryPoint
class StartActivity : AppCompatActivity() {

    private var _binding: ActivityStartBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeColorStatusBar(true)

    }

}

