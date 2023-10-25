package com.demo.med.common.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import com.google.gson.Gson

fun View.show() {
    this.visibility = View.VISIBLE
}

fun <A> String.fromJson(type: Class<A>): A {
    return Gson().fromJson(this, type)
}

fun <A> A.toJson(): String? {
    return Gson().toJson(this)
}


fun Activity.shortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

@Composable
fun <viewModel : LifecycleObserver> viewModel.observeLifecycleEvents(lifecycle: Lifecycle) {
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(this@observeLifecycleEvents)
        onDispose {
            lifecycle.removeObserver(this@observeLifecycleEvents)
        }
    }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}