package com.example.android_architecture_example.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity:UserEntity):Long

    @Query("SELECT * FROM users")
    suspend fun get(): List<UserEntity>
}