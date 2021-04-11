package com.picpay.desafio.android.data.repository

import androidx.annotation.WorkerThread
import com.picpay.desafio.android.core.Configuration
import com.picpay.desafio.android.data.local.UsersDao
import com.picpay.desafio.android.data.model.User
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

class UserRepository(configuration: Configuration, private val usersDao: UsersDao) {

    private val service = configuration.getService()

    fun requestUsers(): Single<List<User>> = service.requestUsers()

    @WorkerThread
    fun getUsers(): Flow<List<User>> {
        return usersDao.getAllUsers()
    }

    @WorkerThread
    suspend fun deleteAllUsers() {
        usersDao.deleteAll()
    }

    @WorkerThread
    suspend fun saveUsers(users: List<User>) {
        usersDao.saveUsers(users)
    }
}