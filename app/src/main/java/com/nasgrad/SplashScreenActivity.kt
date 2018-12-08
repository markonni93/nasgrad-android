package com.nasgrad

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.nasgrad.api.model.IssueCategory
import com.nasgrad.api.model.IssueType
import com.nasgrad.utils.Helper
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
                { result -> saveTypesToSharedPreferences(result)
                    finish()
                },
                { error -> Log.e("sonja types", error.message) }
            )

        disposable2 = client.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> saveCategoriesToSharedPreferences(result)
                    finish()
                },
                { error -> Log.e("sonja categories", error.message) }
            )
    }

    private fun saveTypesToSharedPreferences(issueTypes: List<IssueType>) {
        val map = Helper.issueTypes
        for (type in issueTypes) {
            Log.e("sonja types", "${type.name}")
            map[type.id] = type
        }
    }

    private fun saveCategoriesToSharedPreferences(issueCategories: List<IssueCategory>) {
        val map = Helper.issueCategories
        for (category in issueCategories) {
            Log.e("sonja categories", "${category.name}")
            map[category.id] = category
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable1?.dispose()
    }
}