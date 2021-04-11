package com.picpay.desafio.android.core

import com.picpay.desafio.android.data.network.UserService

interface Configuration {
    fun getService(): UserService
}