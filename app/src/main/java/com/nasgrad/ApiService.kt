package com.nasgrad

import com.nasgrad.api.model.IssueResponse
import retrofit2.http.GET
import io.reactivex.Observable
import retrofit2.Response

interface ApiService {

    @GET("/getissuelist")
    fun getAllIssues(): Observable<Response<IssueResponse>>
}