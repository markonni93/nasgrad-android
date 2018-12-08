package com.nasgrad

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SplashScreenActivity : AppCompatActivity() {

    val client by lazy {
        ApiClient.create()
    }
    var disposable1: Disposable? = null
    var disposable2: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)


        disposable1 = client.getTypes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> Log.e("sonja types", "${result[0].name}")
                    finish()
                },
                { error -> Log.e("sonja types", error.message) }
            )

        disposable2 = client.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> Log.e("sonja categories", "${result[0].name}")
                    finish()
                },
                { error -> Log.e("sonja categories", error.message) }
            )

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable1?.dispose()
    }
}