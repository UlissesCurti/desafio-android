package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.local.UsersDatabase
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object MainModule {
    val instance = module {
        viewModel {
            MainViewModel(
                UserRepository(
                    get(),
                    UsersDatabase.getDatabase(androidContext()).usersDao()
                )
            )
        }
    }
}