package com.nasgrad

import com.nasgrad.api.model.Issue
import com.nasgrad.api.model.IssueCategory
import com.nasgrad.api.model.IssueType
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiClient {

    @GET("getIssueList")
    fun getAllIssues(): Observable<List<Issue>>

    @GET("getIssueList/getIssueDetails/{id}")
    fun getIssueItemById(@Path("id") id: String): Observable<Issue>

    @GET("configuration")
    fun getTypes(): Observable<List<IssueType>>

    @GET("category")
    fun getCategories(): Observable<List<IssueCategory>>

    @POST("newIssue")
    fun createNewIssue(): Observable<Unit>

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