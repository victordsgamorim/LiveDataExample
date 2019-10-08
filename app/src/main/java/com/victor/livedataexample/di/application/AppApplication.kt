package com.victor.livedataexample.di.application

import android.app.Application
import com.victor.livedataexample.di.modules.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(appModule)
        }
    }
}