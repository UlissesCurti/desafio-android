package com.picpay.desafio.android.core

import com.picpay.desafio.android.data.network.UserService
import com.picpay.desafio.android.data.repository.UserRepository

interface Configuration {
    fun getService(): UserService
}