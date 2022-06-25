package com.theairsoft.e_book.database

import android.content.Intent
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theairsoft.e_book.MainActivity
import com.theairsoft.e_book.SharedPrefs
import com.theairsoft.e_book.showSnackbar
import com.theairsoft.e_book.ui.enter.SignInFragment
import com.theairsoft.e_book.ui.enter.SignUpFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {
    suspend fun insertUser(user: UserEntity) = repository.insertUser(user)

    suspend fun updateUser(user: UserEntity) = repository.updateUser(user)

    suspend fun deleteUser(user: UserEntity) = repository.deleteUser(user)

    suspend fun deleteUserById(id: Int) = repository.deleteUserById(id)

    fun getUserByPhone(phone: String) = repository.getUserByPhone(phone)

    fun checkData(userData: UserEntity, fragment: Fragment) {
        userData.mobilePhone?.let {
            getUserByPhone(userData.mobilePhone).also { user ->
                if (user == null) {
                    insertData(userData, fragment)
                } else {
                    fragment.showSnackbar("User registered by this phone number")
                }
            }
        }
    }

    fun login(phone: String,password:String,fragment: Fragment){
        getUserByPhone(phone).also { user->
            if (user==null){
                fragment.showSnackbar("User not found")
            }else{
                if (user.password==password){
                    SharedPrefs(fragment.requireContext()).setFirstTime(true)
                    val intent = Intent(fragment.requireContext(), MainActivity::class.java)
                    fragment.requireActivity().startActivity(intent)
                }else{
                    fragment.showSnackbar("Login or Password error!")
                }
            }
        }
    }

    private fun insertData(user: UserEntity, fragment: Fragment) {
        viewModelScope.launch {
            insertUser(user).also {
                fragment.showSnackbar("You are registered")
                delay(1000)
                SharedPrefs(fragment.requireContext()).setFirstTime(true)
                val intent = Intent(fragment.requireContext(), MainActivity::class.java)
                fragment.requireActivity().startActivity(intent)
            }
        }
    }
}