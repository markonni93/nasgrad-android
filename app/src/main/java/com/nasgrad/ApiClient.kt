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

    @POST("/api/newIssue")
    @Headers("Content-Type: application/json")
    fun createNewIssue(@Body body: NewItemRequest): Observable<Response<ResponseBody>>

    @GET("CRUD/GetAllCityServices")
    fun getAllCityCervices(): Observable<List<CityService>>

    @GET("CRUD/GetAllTypes")
    fun getAllTypes(): Observable<List<Type>>

    @GET("CRUD/GetAllCityServiceTypes")
    fun getAllCityCerviceTypes(): Observable<List<CityCerviceType>>

    companion object {

        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()!!

        fun create(): ApiClient {
            val retrofit = Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_DOMAIN_URL)
                .build()

            return retrofit.create(ApiClient::class.java)
        }
    }
}