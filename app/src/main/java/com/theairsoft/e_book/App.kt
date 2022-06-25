package com.theairsoft.e_book

import android.app.Application
import com.theairsoft.e_book.database.UserDatabase

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        UserDatabase(this)
    }
}