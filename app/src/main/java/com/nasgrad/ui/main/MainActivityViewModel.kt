package com.nasgrad.ui.main

import com.nasgrad.repository.ApiRepository
import com.nasgrad.ui.base.BaseViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val apiRepository: ApiRepository) : BaseViewModel() {
}