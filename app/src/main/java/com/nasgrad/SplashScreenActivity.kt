package com.nasgrad

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nasgrad.base.BaseActivity
import com.nasgrad.repository.ApiRepository
import dagger.android.AndroidInjection
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class SplashScreenActivity : BaseActivity() {

    @Inject
    internal lateinit var apiClient: ApiClient

    @Inject
    internal lateinit var apiRepository: ApiRepository

    var disposable1: Disposable? = null
    var disposable2: Disposable? = null
    var disposable3: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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