package com.example.android_architecture_example.network

import com.squareup.moshi.JsonClass


// Holds list of Users
@JsonClass(generateAdapter = true)
data class UserNetworkContainer(val userDTOS:List<UserDTO>)

// User represents short information about person
@JsonClass(generateAdapter = true)
data class UserDTO(
    val id:String,
    // title maybe null but im not sure
    val title:String,
    val firstName:String,
    val lastName:String,
    val picture:String
)



