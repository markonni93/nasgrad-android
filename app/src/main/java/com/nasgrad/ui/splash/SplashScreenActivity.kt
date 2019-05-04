package com.nasgrad.ui.splash

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.nasgrad.ApiClient
import com.nasgrad.repository.ApiRepository
import com.nasgrad.ui.MainActivity
import com.nasgrad.ui.base.BaseActivity
import javax.inject.Inject

class SplashScreenActivity : BaseActivity() {

    @Inject
    internal lateinit var apiClient: ApiClient

    @Inject
    internal lateinit var apiRepository: ApiRepository

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var splashActivityViewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashActivityViewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashScreenViewModel::class.java)

        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        getDataFromRest()
    }

    private fun getDataFromRest() {
        splashActivityViewModel.getAllTypes()
        splashActivityViewModel.getCityService()
        splashActivityViewModel.getCityServiceTypes()
    }
}