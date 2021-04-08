package com.picpay.desafio.android.domain

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.repository.UserRepository
import io.reactivex.rxjava3.core.Single

class UserUseCase (private val userRepository: UserRepository) {

    fun getUsers(): Single<List<User>> = userRepository.getUsers()
}