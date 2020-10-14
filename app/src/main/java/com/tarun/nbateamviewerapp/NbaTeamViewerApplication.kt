package com.tarun.nbateamviewerapp

import android.app.Application
import com.tarun.nbateamviewerapp.di.helperModule
import com.tarun.nbateamviewerapp.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NbaTeamViewerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    /**
     * Initializes and starts the Koin framework.
     */
    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@NbaTeamViewerApplication)
            modules(helperModule, networkModule)
        }
    }
}