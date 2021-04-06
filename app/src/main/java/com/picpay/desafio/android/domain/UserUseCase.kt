package com.picpay.desafio.android.domain

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.repository.UserRepository
import retrofit2.Call

class UserUseCase (private val userRepository: UserRepository) {

    fun getUsers(): Call<List<User>> = userRepository.getUsers()
}