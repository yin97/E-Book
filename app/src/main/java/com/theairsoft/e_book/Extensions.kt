package com.theairsoft.e_book

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.google.android.material.snackbar.Snackbar
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy
import com.theairsoft.e_book.di.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*

const val NEWS_ID = "NEWS_ID"
const val USER_ID = "USER_ID"

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

fun <T, A> performGetOperation(
    databaseQuery: () -> Flow<T>,
    networkCall: suspend () -> Resource<A>,
    saveCallResult: suspend (A) -> Unit
): Flow<Resource<T>> = flow {
    emit(Resource.loading())
    val source = databaseQuery.invoke().map { Resource.success(it) }.first()
    emit(source)

    val responseStatus = networkCall.invoke()
    if (responseStatus.status == Resource.Status.SUCCESS) {
        saveCallResult(responseStatus.data!!)
        emit(databaseQuery.invoke().map { Resource.success(it) }.first())
    } else if (responseStatus.status == Resource.Status.ERROR) {
        emit(Resource.error(responseStatus.message!!))
        emit(databaseQuery.invoke().map { Resource.success(it) }.first())
    }
}

fun Fragment.getDialogProgressBar(): AlertDialog.Builder {
    var builder: AlertDialog.Builder? = null

    if (builder == null) {
        builder = AlertDialog.Builder(this.requireContext())
        val progressBar = ProgressBar(this.requireContext())
        val lp: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        progressBar.layoutParams = lp
        builder.setView(progressBar)
    }
    return builder
}

fun Date.getDateFormatter(): SimpleDateFormat {
    val calendarDate = Calendar.getInstance().apply {
        time = this@getDateFormatter
    }

    return if (calendarDate.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)) {
        SimpleDateFormat("d MMMM", Locale.ENGLISH)
    } else {
        SimpleDateFormat("d MMMM YYY", Locale.ENGLISH)
    }
}

fun Date.toDisplayString(): String {
    return this.getDateFormatter().format(this)
}