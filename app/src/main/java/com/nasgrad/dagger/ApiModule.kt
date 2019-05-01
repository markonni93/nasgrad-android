package com.nasgrad.dagger

import com.nasgrad.ApiClient
import com.nasgrad.BuildConfig
import com.nasgrad.repository.ApiRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
            return httpClient.build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_DOMAIN_URL)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideApiService(retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }

    @Provides
    @Singleton
    internal fun provideApiRepository(apiClient: ApiClient): ApiRepository {
        return ApiRepository(apiClient)
    }
}