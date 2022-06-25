package com.theairsoft.e_book.database

import androidx.lifecycle.LiveData

class UserRepository(
    private val userDatabase: UserDatabase
) {

    suspend fun insertUser(user: UserEntity) = userDatabase.getUserDao().insertUser(user)

    suspend fun updateUser(user: UserEntity) = userDatabase.getUserDao().updateUser(user)

    suspend fun deleteUser(user: UserEntity) = userDatabase.getUserDao().deleteUser(user)

    suspend fun deleteUserById(id: Int) = userDatabase.getUserDao().deleteUserById(id)

    fun getUserByPhone(phone: String): UserEntity = userDatabase.getUserDao().getUserByLogin(phone)

}