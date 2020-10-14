package com.tarun.nbateamviewerapp.data.remote

import android.content.Context
import com.tarun.nbateamviewerapp.ui.extensions.isNetworkAvailable
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class CachingControlInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()

        // Add Cache Control only for GET methods
        if (request.method() == "GET") {
            request = if (context.isNetworkAvailable()) {
                // 1 day
                request.newBuilder()
                    .header("Cache-Control", "only-if-cached")
                    .build()
            } else {
                // 4 weeks stale
                request.newBuilder()
                    .header("Cache-Control", "public, max-stale=2419200")
                    .build()
            }
        }

        val originalResponse = chain.proceed(request)
        return originalResponse.newBuilder()
            .header("Cache-Control", "max-age=600")
            .build()
    }
}