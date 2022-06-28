package com.theairsoft.e_book.di

import com.theairsoft.e_book.database.NewsDao
import com.theairsoft.e_book.database.UserEntity
import com.theairsoft.e_book.performGetOperation
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val remoteDataSource: NewsRemoteDataSource,
    private val localDataSource: NewsDao
) {
    fun getNews() = performGetOperation(
        databaseQuery = { localDataSource.getAllNews() },
        networkCall = { remoteDataSource.getNews() },
        saveCallResult = { localDataSource.insertAll(it.newsEntityList()) }
    )

    fun getBooks() = performGetOperation(
        databaseQuery = { localDataSource.getAllBooks() },
        networkCall = { remoteDataSource.getBooks() },
        saveCallResult = {
            it.results?.lists!![0].let { it1 ->
                localDataSource.insertAllBooks(
                    it1.booksEntityList()
                )
            }
        }
    )

    fun getNews(id: Long) = localDataSource.getNews(id)

    suspend fun insertUser(user: UserEntity) = localDataSource.insertUser(user)

    fun getUserByPhone(phone: String) = localDataSource.getUserByLogin(phone)

    fun getUserById(id: Int) = localDataSource.getUserById(id)

}