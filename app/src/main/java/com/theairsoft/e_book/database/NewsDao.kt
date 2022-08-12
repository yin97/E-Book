package com.theairsoft.e_book.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news_table")
    fun getAllNews(): Flow<List<NewsLocal>>

    @Query("SELECT * FROM news_table WHERE _id = :id")
    fun getNews(id: Long): Flow<NewsLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsLocal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: NewsLocal)

    @Query("SELECT * FROM book_table")
    fun getAllBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM book_table WHERE _id = :id")
    fun getBook(id: Long): Flow<BookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBooks(news: List<BookEntity>)

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user_table WHERE mobilePhone=:phone")
    fun getUserByLogin(phone: String): UserEntity

    @Query("SELECT * FROM user_table WHERE _id=:id")
    fun getUserById(id:Int):UserEntity
}