package com.theairsoft.e_book.di

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.theairsoft.e_book.database.BookEntity
import com.theairsoft.e_book.database.NewsLocal
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
):ViewModel() {

    val news:LiveData<Resource<List<NewsLocal>>> = repository.getNews()

    val books:LiveData<Resource<List<BookEntity>>> = repository.getBooks()
}