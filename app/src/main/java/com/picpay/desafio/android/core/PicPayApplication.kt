package com.picpay.desafio.android.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PicPayApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PicPayApplication)
            modules(listOf(BaseModule.instance))
        }
    }
}