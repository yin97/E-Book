package com.theairsoft.e_book

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.fragment.app.Fragment

fun Activity.changeColorStatusBar(isChange: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val decor: View = this.window.decorView
        if (isChange) {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decor.systemUiVisibility = 0
        }
    }
}