package com.theairsoft.e_book

import android.Manifest
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.provider.OpenableColumns
import android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.shockwave.pdfium.PdfDocument
import com.shockwave.pdfium.PdfDocument.Bookmark
import com.theairsoft.e_book.databinding.ActivityShowPdfBinding


class ShowPdfActivity : AppCompatActivity(), OnPageChangeListener, OnLoadCompleteListener,
    OnPageErrorListener {

    lateinit var binding: ActivityShowPdfBinding

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSION_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        WRITE_EXTERNAL_STORAGE
    )

    var pageNumber = 0
    var pdfFileName: String? = null

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                if (uri != null) {
                    openPDF(uri)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeColorStatusBar(true)

        verifyStoragePermission(this)

        binding.openBook.setOnClickListener {
            val intent = Intent()
            intent.type = "application/pdf"
            intent.action = Intent.ACTION_GET_CONTENT
            resultLauncher.launch(intent)
        }

    }

    fun verifyStoragePermission(activity: Activity?) {
        val permission = ActivityCompat.checkSelfPermission(activity!!, WRITE_EXTERNAL_STORAGE)

        if (SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager() && permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    activity,
                    PERMISSION_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
                )

                val intent = Intent()
                intent.action = ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                val uri = Uri.fromParts("package", this.packageName, null)
                intent.data = uri
                startActivity(intent)
            }
        }
    }

    private fun openPDF(uri:Uri) {
        pdfFileName = getFileName(uri)

        binding.pdfView.fromUri(uri)
            .defaultPage(pageNumber)
            .onPageChange(this)
            .enableAnnotationRendering(true)
            .onLoad(this)
            .scrollHandle(DefaultScrollHandle(this))
            .spacing(10) // in dp
            .onPageError(this)
            .load();
    }

    override fun loadComplete(nbPages: Int) {
        val meta: PdfDocument.Meta = binding.pdfView.getDocumentMeta()
        printBookmarksTree(binding.pdfView.getTableOfContents(), "-")
    }

    override fun onPageChanged(page: Int, pageCount: Int) {
        pageNumber = page
    }

    fun printBookmarksTree(tree: List<Bookmark>, sep: String) {
        for (b in tree) {
            if (b.hasChildren()) {
                printBookmarksTree(b.children, "$sep-")
            }
        }
    }

    override fun onPageError(page: Int, t: Throwable?) {
        Log.e("pdfFile", "Cannot load page " + page);
    }

    fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor?.close()
            }
        }
        if (result == null) {
            result = uri.lastPathSegment
        }
        return result
    }
}
