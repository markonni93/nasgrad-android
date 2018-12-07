package com.nasgrad.api

import com.nasgrad.api.model.Issue
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/api/getIssueDetails?id={id}")
    fun getIssueItemById(@Path("id") id: String): Observable<Issue>
}