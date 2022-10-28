package com.woody.network.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {
    fun create(baseUrl: String, vararg interceptors: Interceptor): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildClient(*interceptors))
            .build()
    }

    private fun buildClient(vararg interceptors: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
//            if (BuildConfig.DEBUG) {
//                addInterceptor(
//                    HttpLoggingInterceptor().apply {
//                        setLevel(HttpLoggingInterceptor.Level.BODY)
//                    }
//                )
//            }
            interceptors.forEach { interceptor ->
                addInterceptor(interceptor)
            }
        }.build()
    }
}