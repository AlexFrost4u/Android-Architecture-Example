package com.example.android_architecture_example.database

import androidx.room.PrimaryKey

data class DatabaseEntities constructor(
    val email: String,
    val firstName: String,
    @PrimaryKey
    val id: String,
    val lastName: String,
    val picture: String,
    val title: String
)