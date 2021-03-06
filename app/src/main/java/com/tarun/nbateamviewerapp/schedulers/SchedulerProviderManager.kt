package com.tarun.nbateamviewerapp.schedulers

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * A convenience class for getting all required schedulers.
 */
class SchedulerProviderManager : SchedulerProvider {
    override fun io(): Scheduler? {
        return Schedulers.io()
    }

    override fun ui(): Scheduler? {
        return AndroidSchedulers.mainThread()
    }
}