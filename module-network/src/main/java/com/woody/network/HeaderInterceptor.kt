package com.woody.network

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(
    private val header: Map<String, String>,
    private val keepOldHeader: Boolean = false
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val newRequest = oldRequest.newBuilder()
            .apply {
                if (keepOldHeader) {
                    val oldHeaders = oldRequest.headers()
                    for (i in 0 until oldHeaders.size()) {
                        addHeader(oldHeaders.name(i), oldHeaders.value(i))
                    }
                }
                for ((k, v) in header) {
                    addHeader(k, v)
                }
            }
            .build()
        return chain.proceed(newRequest)
    }
}