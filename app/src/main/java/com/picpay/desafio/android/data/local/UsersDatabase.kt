package com.picpay.desafio.android.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.picpay.desafio.android.data.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun usersDao(): UsersDao

    companion object {
        fun getDatabase(context: Context): UsersDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                UsersDatabase::class.java,
                "users_database"
            ).build()
        }
    }
}