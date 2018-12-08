package com.nasgrad

import com.nasgrad.api.model.*
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiClient {

    @GET("getIssueList")
    fun getAllIssues(): Observable<List<Issue>>

    @GET("getIssueList/getIssueDetails/{id}")
    fun getIssueItemById(@Path("id") id: String): Observable<Issue>

    @GET("configuration")
    fun getTypes(): Observable<List<IssueType>>

    @GET("category")
    fun getCategories(): Observable<List<IssueCategory>>

    @Multipart
    @POST("/api/newIssue")
    @Headers("Content-Type: application/json")
    fun createNewIssue(@Part("issue") issue: IssueRequestBody, @Part("pictureInfo") pictureInfo: PictureInfo): Observable<Response<ResponseBody>>

    companion object {

        val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()!!

        fun create(): ApiClient {
            val retrofit = Retrofit.Builder()
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://68.183.223.223:8080/api/")
                    .build()

            return retrofit.create(ApiClient::class.java)
        }
    }
}