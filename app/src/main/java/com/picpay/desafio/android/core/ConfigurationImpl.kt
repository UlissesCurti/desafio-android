package com.picpay.desafio.android.core

import com.picpay.desafio.android.data.network.UserService
import retrofit2.Retrofit

class ConfigurationImpl(private val retrofit: Retrofit) : Configuration {

    override fun getService(): UserService {
        return retrofit.create(UserService::class.java)
    }
}