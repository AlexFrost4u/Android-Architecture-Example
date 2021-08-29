package com.example.android_architecture_example.network

import com.example.android_architecture_example.domain.UserFull
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface UserRetrofit {
    @Headers("app-id: 611e46f225e439a3e4263f5a")
    @GET("user?page=0&limit=50")
    suspend fun getAll():UserRaw

    @Headers("app-id: 611e46f225e439a3e4263f5a")
    @GET("user/{id}")
    suspend fun getUser(@Path("id")id:String): UserFullDTO
}