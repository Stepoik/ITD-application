package com.itd.app

import android.app.Application
import com.itd.app.di.rootModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ITDApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ITDApp)
            modules(rootModule)
        }
    }
}