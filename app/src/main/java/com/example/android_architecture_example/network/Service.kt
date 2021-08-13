package com.example.android_architecture_example.network

import retrofit2.http.GET

interface UserRetrofit {
    @GET("user")
    suspend fun get():List<UserDTO>
}