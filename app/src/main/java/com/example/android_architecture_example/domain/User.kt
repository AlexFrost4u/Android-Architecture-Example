package com.example.android_architecture_example.domain

data class User(
    val firstName: String,
    val id: String,
    val lastName: String,
    val picture: String,
    // title can be empty
    val title: String
)