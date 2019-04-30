package com.nasgrad

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.nasgrad.api.model.*
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
    var disposable3: Disposable? = null
    var disposable4: Disposable? = null
    var disposable5: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)


        disposable1 = client.getTypes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    saveTypesToSharedPreferences(result)
                    finish()
                },
                { error -> finish() }
            )

        disposable2 = client.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    saveCategoriesToSharedPreferences(result)
                    finish()
                },
                { error -> Log.e("sonja categories", error.message) }
            )

        disposable3 = client.getAllCityCervices()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    saveCityCervices(result)
                    finish()
                },
                { error -> finish() }
            )

        disposable4 = client.getAllTypes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    saveAllTypes(result)
                    finish()
                },
                { error -> finish() }
            )

        disposable5 = client.getAllCityCerviceTypes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    saveCityCerviceTypes(result)
                    finish()
                },
                { error -> finish() }
            )
    }

    private fun saveCityCerviceTypes(cityCerviceTypes: List<CityCerviceType>) {
        val map = Helper.cityServicesTypes
        for (cityCerviceType in cityCerviceTypes) {
            map.put(cityCerviceType.id, cityCerviceType)
        }
    }

    private fun saveAllTypes(types: List<Type>) {
        val map = Helper.allTypes
        for (type in types) {
            map.put(type.id, type)
        }
    }

    private fun saveCityCervices(cityCervices: List<CityService>) {
        val map = Helper.cityServices
        for (cityCervice in cityCervices) {
            map.put(cityCervice.id, cityCervice)
        }
    }

    private fun saveTypesToSharedPreferences(issueTypes: List<IssueType>) {
        val map = Helper.issueTypes
        for (type in issueTypes) {
            Log.e("sonja types", "${type.name}")
            map.put(type.id, type)
            //map[type.id] = type
        }
    }

    private fun saveCategoriesToSharedPreferences(issueCategories: List<IssueCategory>) {
        val map = Helper.issueCategories
        for (category in issueCategories) {
            Log.e("sonja categories", "${category.name}")
            map.put(category.id, category)
            //map[category.id] = category
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable1?.dispose()
        disposable2?.dispose()
        disposable3?.dispose()
        disposable4?.dispose()
        disposable5?.dispose()
    }
}