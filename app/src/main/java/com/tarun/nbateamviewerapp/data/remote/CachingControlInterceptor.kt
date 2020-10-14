package com.tarun.nbateamviewerapp.data.remote

import android.content.Context
import com.tarun.nbateamviewerapp.ui.extensions.isNetworkAvailable
import okhttp3.Interceptor
import okhttp3.Response

class CachingControlInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        return if (context.isNetworkAvailable()) {
            val maxAge = 60 // read from cache for 1 minute
            originalResponse.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .build()
        } else {
            val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
            originalResponse.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .build()
        }
    }
}