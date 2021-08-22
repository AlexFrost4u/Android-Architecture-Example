package com.example.android_architecture_example.network

data class UserRaw(
    val `data`: List<UserDTO>,
    val limit: Int,
    val page: Int,
    val total: Int
)