package com.example.android_architecture_example.network

import com.squareup.moshi.JsonClass


// Holds list of Users
@JsonClass(generateAdapter = true)
data class NetworkUserContainer(val users:List<NetworkUser>)

// User represents short information about person
@JsonClass(generateAdapter = true)
data class NetworkUser(
    val id:String,
    // title maybe null but im not sure
    val title:String,
    val firstName:String,
    val lastName:String,
    val email:String,
    val picture:String
)

