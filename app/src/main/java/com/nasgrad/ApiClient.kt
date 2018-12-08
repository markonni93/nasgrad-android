package com.nasgrad

import com.nasgrad.api.model.Issue
import com.nasgrad.api.model.IssueResponse
import retrofit2.http.GET
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path

interface ApiClient {

    @GET("getIssueList")
    fun getAllIssues(): Observable<List<Issue>>

    @GET("getIssueDetails?id={id}")
    fun getIssueItemById(@Path("id") id: String): Observable<Issue>

    companion object {
        fun create(): ApiClient {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://68.183.223.223:8080/api/")
                .build()

            return retrofit.create(ApiClient::class.java)
        }
    }
}