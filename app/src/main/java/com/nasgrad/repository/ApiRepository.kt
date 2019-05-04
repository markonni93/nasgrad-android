package com.nasgrad.repository

import com.nasgrad.ApiClient
import com.nasgrad.api.model.CityCerviceType
import com.nasgrad.api.model.CityService
import com.nasgrad.api.model.Type
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ApiRepository @Inject constructor(var apiClient: ApiClient) {

    fun getTypes(): Observable<List<Type>> {
        return apiClient.getAllTypes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getCityServices(): Observable<List<CityService>> {
        return apiClient.getAllCityCervices()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getCityServiceTypes(): Observable<List<CityCerviceType>> {
        return apiClient.getAllCityCerviceTypes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}