package com.example.android_architecture_example.network

import retrofit2.http.GET
import retrofit2.http.Headers

interface UserRetrofit {
    @Headers("app-id: 611e46f225e439a3e4263f5a")
    @GET("user?page=0&limit=50")
    suspend fun get():UserRaw
}