package com.nasgrad.dagger

import com.nasgrad.MainActivity
import com.nasgrad.SplashScreenActivity
import com.nasgrad.issue.CreateIssueActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeCreateIssueActivity(): CreateIssueActivity

    @ContributesAndroidInjector
    abstract fun contributesSplashActivity(): SplashScreenActivity
}