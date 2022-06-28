package com.theairsoft.e_book.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.theairsoft.e_book.ui.home.BookData
import com.theairsoft.e_book.ui.home.NewsItem

@Entity(tableName = "news_table")
data class NewsLocal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Long? = null,
    val publishedDate: String,
    val title: String?,
    val subTitle: String?,
    val image: String?
) {
    fun toNewsData(): NewsItem {
        return NewsItem(
            id = id,
            title = title,
            subTitle = subTitle,
            image = image,
            publishedDate = publishedDate
        )
    }
}