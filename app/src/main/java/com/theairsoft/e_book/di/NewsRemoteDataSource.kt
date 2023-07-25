package com.theairsoft.e_book.di

import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(
    private val newsService: NewsService
) : BaseDataSource() {
    suspend fun getNews() = getResult { newsService.getNews("L1jc2m9EyGWbGAGgroNAGD8dHXwJATAX") }
    suspend fun getBooks() = getResult { newsService.getBooks() }

    suspend fun getOffers() = getResult { newsService.getOffers("https://www.kattabozor.uz/hh/test/api/v1/offers") }
}