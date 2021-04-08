package com.picpay.desafio.android.core

import org.koin.dsl.module

object BaseModule {
    val instance = module {

        single { NetworkMiddleware.getInstance() }

        factory<Configuration>{
            ConfigurationImpl(get())
        }
    }
}