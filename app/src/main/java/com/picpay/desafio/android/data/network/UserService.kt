package com.picpay.desafio.android.data.network

import com.picpay.desafio.android.data.model.User
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface UserService {

    @GET("users")
    fun requestUsers(): Single<List<User>>
}