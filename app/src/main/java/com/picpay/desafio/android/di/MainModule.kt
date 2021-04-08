package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.domain.UserUseCase
import com.picpay.desafio.android.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object MainModule {
//    val instance = module {
//
//        viewModel { MainViewModel(get()) }
//
//        scope(named<MainActivity>()) {
//            scoped { UserRepository(get()) }
//            scoped { UserUseCase(get()) }
//        }
//    }
    val instance = module {

        viewModel { MainViewModel(UserUseCase(UserRepository(get()))) }
    }
}