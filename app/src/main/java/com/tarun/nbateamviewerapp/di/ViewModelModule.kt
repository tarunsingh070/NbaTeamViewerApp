package com.tarun.nbateamviewerapp.di

import com.tarun.nbateamviewerapp.ui.TeamsSharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TeamsSharedViewModel() }
}