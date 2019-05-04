package com.nasgrad.ui.splash

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.nasgrad.ApiClient
import com.nasgrad.repository.ApiRepository
import com.nasgrad.ui.MainActivity
import com.nasgrad.ui.base.BaseActivity
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class SplashScreenActivity : BaseActivity() {

    @Inject
    internal lateinit var apiClient: ApiClient

    @Inject
    internal lateinit var apiRepository: ApiRepository

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var splashActivityViewModel: SplashScreenViewModel

    var disposable1: Disposable? = null
    var disposable2: Disposable? = null
    var disposable3: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashActivityViewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashScreenViewModel::class.java)

        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        disposable1 = apiRepository.getCityServices()
        disposable2 = apiRepository.getCityServiceTypes()
        disposable3 = apiRepository.getTypes()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable1?.dispose()
        disposable2?.dispose()
        disposable3?.dispose()
    }
}