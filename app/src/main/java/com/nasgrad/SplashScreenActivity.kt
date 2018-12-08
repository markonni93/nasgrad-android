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
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)


//        disposable = client.getIssueTypes()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                { result -> Log.e("sonja", "${result.size}")
//                    finish()
//                },
//                { error -> Log.e("sonja", error.message) }
//            )

    }
}