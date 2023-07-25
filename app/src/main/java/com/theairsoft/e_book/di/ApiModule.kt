package com.theairsoft.e_book.di

import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.theairsoft.e_book.App
import com.theairsoft.e_book.database.AppDatabase
import com.theairsoft.e_book.database.NewsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val BASE_URL = "https://api.nytimes.com/svc/"
const val TEST_BASE_URL = "https://www.kattabozor.uz/"

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, @ApplicationContext appContext: Context): Retrofit =
        Retrofit.Builder().client(
            OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(
                    ChuckerInterceptor.Builder(appContext)
                        .collector(ChuckerCollector(appContext))
                        .maxContentLength(250000L)
                        .redactHeaders(emptySet())
                        .alwaysReadResponseBody(false)
                        .build()
                )
                .build()
        )
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideNewsService(retrofit: Retrofit): NewsService =
        retrofit.create(NewsService::class.java)

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(newsService: NewsService) = NewsRemoteDataSource(newsService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideNewsDao(db: AppDatabase) = db.newsDao()

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: NewsRemoteDataSource,
        localDataSource: NewsDao
    ) = NewsRepository(remoteDataSource, localDataSource)

}