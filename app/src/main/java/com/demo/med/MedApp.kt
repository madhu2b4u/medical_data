package com.demo.med

import android.app.Application
import com.demo.med.common.SpUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MedApp : Application() {

    override fun onCreate() {
        super.onCreate()
        SpUtil.instance?.init(this)
    }
}