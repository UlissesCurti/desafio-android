package com.picpay.desafio.android.viewmodel

import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.domain.UserUseCase
import retrofit2.Callback

class MainViewModel(private val useCase: UserUseCase) : ViewModel() {

    fun getUsers(callback: Callback<List<User>>) {
        useCase.getUsers()
            .enqueue(callback)
    }
}