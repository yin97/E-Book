package com.theairsoft.e_book.database

import androidx.room.*

@Dao
interface UserDao {

    @Insert()
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("SELECT * FROM user_table WHERE mobilePhone=:phone")
    fun getUserByLogin(phone: String): UserEntity

    @Query("DELETE FROM user_table WHERE _id = :id")
    suspend fun deleteUserById(id: Int)

}