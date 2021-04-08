package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.local.UsersDatabase
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.ui.MainActivity
import com.picpay.desafio.android.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
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

//        factory {  }
//        factory { get<UsersDatabase>().usersDao() }
//        scope(named<MainActivity>()) {
//            scoped { UsersDatabase.getDatabase(androidContext())) }
//            scoped { get<UsersDatabase>().usersDao() }
//        }
        viewModel { MainViewModel(UserRepository(get(), UsersDatabase.getDatabase(androidContext()).usersDao())) }
    }
}