package com.example.android_architecture_example.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities=[UserEntity::class],version = 1,exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        val DATABASE_NAME :String = "user_db"
    }
}