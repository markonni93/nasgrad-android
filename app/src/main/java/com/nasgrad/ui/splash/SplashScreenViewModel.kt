package com.nasgrad.ui.splash

import com.nasgrad.repository.ApiRepository
import com.nasgrad.ui.base.BaseViewModel
import com.nasgrad.utils.Helper
import timber.log.Timber
import javax.inject.Inject

class SplashScreenViewModel @Inject constructor(private val apiRepository: ApiRepository) : BaseViewModel() {

    fun getAllTypes() {
        compositeDisposable.add(
            apiRepository.getTypes()
                .subscribe({ result ->
                    val map = Helper.allTypes
                    for (type in result) {
                        map.put(type.id, type)
                    }
                },
                    { error -> Timber.e(error) }
                )
        )
    }

    fun getCityService() {
        compositeDisposable.add(
            apiRepository.getCityServices()
                .subscribe({ result ->
                    val map = Helper.cityServices
                    for (cityService in result) {
                        map.put(cityService.id, cityService)
                    }
                },
                    { error -> Timber.e(error) })
        )
    }

    fun getCityServiceTypes() {
        compositeDisposable.add(
            apiRepository.getCityServiceTypes()
                .subscribe({ result ->
                    val map = Helper.cityServicesTypes
                    for (type in result) {
                        map.put(type.id, type)
                    }
                }, { error -> Timber.e(error) })
        )
    }
}