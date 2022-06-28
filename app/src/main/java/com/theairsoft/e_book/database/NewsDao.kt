package com.theairsoft.e_book.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.theairsoft.e_book.di.NewsEmailedArticle

@Dao
interface NewsDao {
    @Query("SELECT * FROM news_table")
    fun getAllNews(): LiveData<List<NewsLocal>>

    @Query("SELECT * FROM news_table WHERE _id = :id")
    fun getNews(id: Int): LiveData<NewsLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsLocal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: NewsLocal)

    @Query("SELECT * FROM book_table")
    fun getAllBooks(): LiveData<List<BookEntity>>

    @Query("SELECT * FROM book_table WHERE _id = :id")
    fun getBook(id: Int): LiveData<BookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBooks(news: List<BookEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(news: BookEntity)
}