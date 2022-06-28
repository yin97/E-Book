package com.theairsoft.e_book.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.theairsoft.e_book.ui.home.BookData

@Entity(tableName = "book_table")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Long?=null,
    val title:String?,
    val author: String?,
    val url: String?,
    val image:String?
){
    fun toBookData(): BookData {
        return BookData(name = title, author = author, image = image)
    }
}