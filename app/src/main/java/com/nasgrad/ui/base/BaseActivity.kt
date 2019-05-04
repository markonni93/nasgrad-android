package com.nasgrad.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity : AppCompatActivity() {

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}