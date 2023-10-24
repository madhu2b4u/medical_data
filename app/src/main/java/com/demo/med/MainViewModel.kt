package com.demo.med

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.demo.med.common.LOGGEDIN
import com.demo.med.common.SingleLiveEvent
import com.demo.med.common.SpUtil

class MainViewModel : ViewModel(), DefaultLifecycleObserver {

    private val sharedPrefsHelpers = SpUtil.instance

    val showLogin: LiveData<Boolean> get() = _showLogin
    private val _showLogin = SingleLiveEvent<Boolean>()

    fun onTimeout() {
        _showLogin.value = sharedPrefsHelpers?.getBoolean(LOGGEDIN)
    }

}