package com.theairsoft.e_book

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy

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

fun Fragment.showSnackbar(snackbarText: String, timeLength: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(requireView(), snackbarText, timeLength)
        .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        .setActionTextColor(ContextCompat.getColor(requireContext(), android.R.color.white)).show()
}

fun EditText.setMaskOn(button: Button? = null, activity: Activity?, textView: TextView? = null) {
    var phoneNumber = ""
    MaskedTextChangedListener.installOn(
        this,
        "+998 [00] [000]-[00]-[00]",
        emptyList(),
        AffinityCalculationStrategy.PREFIX,
        object : MaskedTextChangedListener.ValueListener {
            override fun onTextChanged(
                maskFilled: Boolean,
                extractedValue: String,
                formattedValue: String,
            ) {
                phoneNumber = extractedValue

                val isCorrect = phoneNumber.length == 9
                button?.isEnabled = isCorrect
                textView?.isVisible = !isCorrect

                if (isCorrect) {
                    closeKeyboard(activity)
                }
            }
        }
    )
}

fun View?.closeKeyboard(activity: Activity?) {
    if (this != null) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.windowToken, 0)
    }
}

fun EditText.getMaskedPhoneWithoutSpace(): String {
    var phone = this.text.toString()
    if (phone.startsWith("+"))
        phone = phone.substring(1, phone.length)
    return phone.replace(" ", "").replace("-", "").removeRange(0, 3)
}