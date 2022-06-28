package com.theairsoft.e_book.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_table")
data class NewsLocal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Long?=null,
    val publishedDate:String?,
    val title: String?,
    val subTitle: String?,
)