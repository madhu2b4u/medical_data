package com.demo.med.auth

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.med.common.LOGGEDIN
import com.demo.med.common.SpUtil
import com.demo.med.common.USERNAME

class LoginViewModel : ViewModel(), LifecycleObserver {

    private val sharedPrefsHelpers = SpUtil.instance

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    fun onLogin(username: String, password: String) {
        if (username.isNotEmpty() || password.isNotEmpty()) {
            saveLoginStatus(username)
            _loginSuccess.value = true
        } else {
            _loginSuccess.value = false
        }
    }

    private fun saveLoginStatus(username: String) {
        sharedPrefsHelpers?.putString(USERNAME, username)
        sharedPrefsHelpers?.putBoolean(LOGGEDIN, true)
    }
}
