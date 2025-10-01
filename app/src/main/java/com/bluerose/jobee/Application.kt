package com.bluerose.jobee

import android.app.Application
import com.bluerose.jobee.di.Singletons

class JobeeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Singletons.init(this)
    }
}