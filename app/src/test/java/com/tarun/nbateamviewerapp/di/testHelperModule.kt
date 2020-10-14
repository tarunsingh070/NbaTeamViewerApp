package com.tarun.nbateamviewerapp.di

import com.tarun.nbateamviewerapp.data.remote.TeamsRepository
import com.tarun.nbateamviewerapp.data.remote.TeamsRepositoryImpl
import com.tarun.nbateamviewerapp.schedulers.SchedulerProvider
import com.tarun.nbateamviewerapp.schedulers.TestSchedulerProviderManager
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.koin.dsl.module

val testHelperModule = module {
    single<TeamsRepository> { TeamsRepositoryImpl() }

    single<SchedulerProvider> { TestSchedulerProviderManager(TestScheduler()) }
}