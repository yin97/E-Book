package com.theairsoft.e_book.di

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theairsoft.e_book.*
import com.theairsoft.e_book.database.BookEntity
import com.theairsoft.e_book.database.NewsLocal
import com.theairsoft.e_book.database.UserEntity
import com.theairsoft.e_book.ui.home.BookData
import com.theairsoft.e_book.ui.home.NewsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    val books = MutableLiveData<List<BookData>>()
    val new = MutableLiveData<NewsItem>()
    val news = MutableLiveData<List<NewsItem>>()
    val user = MutableLiveData<UserEntity>()

    fun getNews(fragment: Fragment) {
        viewModelScope.launch {
            try {
                val progressDialog = fragment.getDialogProgressBar().create()
                progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                repository.getNews().onEach { res ->
                    when (res.status) {
                        Resource.Status.SUCCESS -> {
                            progressDialog.dismiss()
                            if (!res.data.isNullOrEmpty()) {
                                val list = ArrayList(res.data)
                                val news = ArrayList<NewsItem>()
                                list.let {
                                    it.forEach { e ->
                                        news.add(e.toNewsData())
                                    }
                                }
                                this@NewsViewModel.news.postValue(news)
                            }
                        }
                        Resource.Status.ERROR -> {
                            res.message?.let { fragment.showSnackbar(it) }
                        }

                        Resource.Status.LOADING -> {
                            progressDialog.show()
                        }
                    }
                }.launchIn(viewModelScope)
            } catch (e: Exception) {
                e.message?.let { fragment.showSnackbar(it) }
            }
        }
    }

    fun getBooks(fragment: Fragment) {
        viewModelScope.launch {
            try {
                val progressDialog = fragment.getDialogProgressBar().create()
                progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                repository.getBooks().onEach { res ->
                    when (res.status) {
                        Resource.Status.SUCCESS -> {
                            progressDialog.dismiss()
                            if (!res.data.isNullOrEmpty()) {
                                val list = ArrayList(res.data)
                                val books = ArrayList<BookData>()
                                list.let {
                                    it.forEach { e ->
                                        books.add(e.toBookData())
                                    }
                                }
                                this@NewsViewModel.books.postValue(books)
                            }
                        }
                        Resource.Status.ERROR -> {
                            res.message?.let { fragment.showSnackbar(it) }
                        }

                        Resource.Status.LOADING -> {
                            progressDialog.show()
                        }
                    }
                }.launchIn(viewModelScope)

            } catch (e: Exception) {
                e.message?.let { fragment.showSnackbar(it) }
            }
        }
    }

    fun getNews(fragment: Fragment, id: Long) {
        viewModelScope.launch {
            try {
                val progressDialog = fragment.getDialogProgressBar().create()
                progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                repository.getNews(id).onEach { news ->
                    this@NewsViewModel.new.postValue(news.toNewsData())
                }.launchIn(viewModelScope)

            } catch (e: Exception) {
                e.message?.let { fragment.showSnackbar(it) }
            }
        }
    }


    suspend fun insertUser(user: UserEntity) = repository.insertUser(user)


    fun getUserByPhone(phone: String) = repository.getUserByPhone(phone)

    fun getUserById(fragment: Fragment, id: Int) {
        viewModelScope.launch {
            try {
                repository.getUserById(id).also { user ->
                    this@NewsViewModel.user.postValue(user)
                }
            } catch (e: Exception) {
                e.message?.let { message -> fragment.showSnackbar(message) }
            }
        }
    }

    fun checkData(userData: UserEntity, fragment: Fragment) {
        viewModelScope.launch {
            try {
                userData.mobilePhone?.let {
                    getUserByPhone(it).also { user ->
                        if (user == null) {
                            insertData(userData, fragment)
                        } else {
                            fragment.showSnackbar("User registered by this phone number")
                        }
                    }
                }
            } catch (e: Exception) {
                e.message?.let { message -> fragment.showSnackbar(message) }
            }
        }
    }

    fun login(phone: String, password: String, fragment: Fragment) {
        try {
            getUserByPhone(phone).also { user ->
                if (user == null) {
                    fragment.showSnackbar("User not found")
                } else {
                    if (user.password == password) {
                        SharedPrefs(fragment.requireContext()).setFirstTime(true)
                        val intent = Intent(fragment.requireContext(), MainActivity::class.java)
                        user.id?.let { SharedPrefs(fragment.requireContext()).setUserId(it) }
                        fragment.requireActivity().startActivity(intent)
                    } else {
                        fragment.showSnackbar("Login or Password error!")
                    }
                }
            }
        } catch (e: Exception) {
            e.message?.let { message -> fragment.showSnackbar(message) }
        }
    }

    private fun insertData(user: UserEntity, fragment: Fragment) {
        viewModelScope.launch {
            insertUser(user).also {
                fragment.showSnackbar("You are registered")
                SharedPrefs(fragment.requireContext()).apply {
                    setFirstTime(true)
                    user.id?.let { it1 -> setUserId(it1) }
                }
                val intent = Intent(fragment.requireContext(), MainActivity::class.java)
                fragment.requireActivity().startActivity(intent)
            }
        }
    }
}