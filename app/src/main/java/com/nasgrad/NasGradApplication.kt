package com.nasgrad

import android.app.Activity
import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import com.nasgrad.dagger.AppComponent
import com.nasgrad.dagger.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.fabric.sdk.android.Fabric
import javax.inject.Inject

class NasGradApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)


    }

    override fun getApplicationContext(): Context {
        return super.getApplicationContext()
    }
}