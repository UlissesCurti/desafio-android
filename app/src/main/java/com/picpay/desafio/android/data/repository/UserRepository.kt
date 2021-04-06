package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.core.Configuration
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.network.UserService
import retrofit2.Call

class UserRepository(configuration: Configuration) {

    private val service = configuration.getService()

    fun getUsers(): Call<List<User>> = service.getUsers()
}