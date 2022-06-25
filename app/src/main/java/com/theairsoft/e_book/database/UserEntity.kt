package com.theairsoft.e_book.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Int?,
    val name: String?,
    val email: String?,
    val mobilePhone: String?,
    val password: String?
)