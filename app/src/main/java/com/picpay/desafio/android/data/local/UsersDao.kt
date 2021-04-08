package com.picpay.desafio.android.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.picpay.desafio.android.data.model.User
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    @Insert
    suspend fun saveUsers(users: List<User>)

    @Query("DELETE FROM users_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM users_table")
    fun getAllUsers(): Flow<List<User>>
}