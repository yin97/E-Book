package com.theairsoft.e_book

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.theairsoft.e_book.databinding.ActivityPdfReaderBinding


class PdfReaderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPdfReaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfReaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeColorStatusBar(true)
        binding.pdfView.fromAsset("book.pdf").show()

    }
}