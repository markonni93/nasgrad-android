package com.nasgrad.ui.splash

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.nasgrad.ui.base.BaseActivity
import com.nasgrad.ui.main.MainActivity

class SplashScreenActivity : BaseActivity() {

    lateinit var splashActivityViewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashActivityViewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashScreenViewModel::class.java)

        getDataFromRest()
        //Todo this needs to be changed by ziping multiple calls in rxjava
        Handler().postDelayed({
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)}, 5000)
    }

    private fun getDataFromRest() {
        splashActivityViewModel.getAllTypes()
        splashActivityViewModel.getCityService()
        splashActivityViewModel.getCityServiceTypes()
    }
}