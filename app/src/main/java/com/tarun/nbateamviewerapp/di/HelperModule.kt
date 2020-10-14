package com.tarun.nbateamviewerapp.di

import com.tarun.nbateamviewerapp.data.remote.TeamsRepository
import com.tarun.nbateamviewerapp.data.remote.TeamsRepositoryImpl
import org.koin.dsl.module

val helperModule = module {
    single<TeamsRepository> { TeamsRepositoryImpl() }
}