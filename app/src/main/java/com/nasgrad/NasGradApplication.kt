package com.nasgrad

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

class NasGradApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
    }

    override fun getApplicationContext(): Context {
        return super.getApplicationContext()
    }
}