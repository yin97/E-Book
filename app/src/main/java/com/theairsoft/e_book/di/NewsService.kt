package com.theairsoft.e_book.di

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("mostpopular/v2/emailed/1.json")
    suspend fun getNews(@Query("api-key") key: String): Response<NewsResponse>

    @GET("books/v3/lists/full-overview.json?api-key=L1jc2m9EyGWbGAGgroNAGD8dHXwJATAX")
    suspend fun getBooks():Response<BookResponse>
}