package com.picpay.desafio.android.data.network

import com.picpay.desafio.android.data.model.User
import retrofit2.Call
import retrofit2.http.GET

interface UserService {

    @GET("users")
    fun getUsers(): Call<List<User>>
}