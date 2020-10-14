package com.tarun.nbateamviewerapp.di

import com.tarun.nbateamviewerapp.data.remote.CachingControlInterceptor
import com.tarun.nbateamviewerapp.data.remote.ApiService
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

val networkModule = module {

    single { get<Retrofit>().create(ApiService::class.java) }

    single {
        Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .client(get())
            .addConverterFactory(get<GsonConverterFactory>())
            .addCallAdapterFactory(get<RxJava3CallAdapterFactory>())
            .build()
    }

    single {
        OkHttpClient.Builder()
            .cache(get<Cache>())
            .addNetworkInterceptor(get<CachingControlInterceptor>())
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        val cacheSize = 10 * 1024 * 1024.toLong() // 10 MiB
        Cache(File(androidContext().cacheDir, "http"), cacheSize)
    }

    single { RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()) }

    single { GsonConverterFactory.create() }

    single { CachingControlInterceptor(androidContext()) }
}