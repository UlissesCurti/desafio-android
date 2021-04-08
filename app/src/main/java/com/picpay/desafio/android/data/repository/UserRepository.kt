package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.core.Configuration
import com.picpay.desafio.android.data.model.User
import io.reactivex.rxjava3.core.Single

class UserRepository(configuration: Configuration) {

    private val service = configuration.getService()

    fun getUsers(): Single<List<User>> = service.getUsers()
}