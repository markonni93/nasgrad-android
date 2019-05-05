package com.nasgrad.dagger

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.nasgrad.ui.main.MainActivityViewModel
import com.nasgrad.ui.splash.SplashScreenViewModel
import com.nasgrad.utils.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SplashScreenViewModel::
    class)
    protected abstract fun splashActivityViewModel(splashActivityViewModel: SplashScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    protected abstract fun mainActivityModule(mainActivityViewModel: MainActivityViewModel): ViewModel
}