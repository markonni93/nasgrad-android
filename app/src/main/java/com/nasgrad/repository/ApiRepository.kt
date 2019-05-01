package com.nasgrad.repository

import com.nasgrad.ApiClient
import com.nasgrad.utils.Helper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class ApiRepository @Inject constructor(var apiClient: ApiClient) {

    fun getTypes(): Disposable {
        val disposable: Disposable

        disposable = apiClient.getAllTypes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    val map = Helper.allTypes
                    for (type in result) {
                        map.put(type.id, type)
                    }
                },
                { error -> Timber.e(error) }
            )
        return disposable
    }

    fun getCityServices(): Disposable {
        val disposable: Disposable

        disposable = apiClient.getAllCityCervices()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    val map = Helper.cityServices
                    for (cityService in result) {
                        map.put(cityService.id, cityService)
                    }
                },
                { error -> Timber.e(error) }
            )
        return disposable
    }

    fun getCityServiceTypes(): Disposable {
        val disposable: Disposable

        disposable = apiClient.getAllCityCerviceTypes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    val map = Helper.cityServicesTypes
                    for (cityCerviceType in result) {
                        map.put(cityCerviceType.id, cityCerviceType)
                    }
                },
                { error -> Timber.e(error) }
            )
        return disposable
    }
}