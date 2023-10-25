package com.demo.med.common.extensions

import android.app.Activity
import android.widget.Toast
import com.google.gson.Gson


fun <A> String.fromJson(type: Class<A>): A {
    return Gson().fromJson(this, type)
}

fun <A> A.toJson(): String? {
    return Gson().toJson(this)
}


fun Activity.shortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}