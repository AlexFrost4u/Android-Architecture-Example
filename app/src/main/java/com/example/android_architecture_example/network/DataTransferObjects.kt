package com.example.android_architecture_example.network

import com.squareup.moshi.JsonClass


// Holds list of Users
@JsonClass(generateAdapter = true)
data class UserNetworkContainer(val userDTOS:List<UserDTO>)

// Data that we get from main query
data class UserRaw(
    val `data`: List<UserDTO>,
    val limit: Int,
    val page: Int,
    val total: Int
)

// Data that we separate from main query to work on
@JsonClass(generateAdapter = true)
data class UserDTO(
    val id:String,
    // title maybe null but im not sure
    val title:String,
    val firstName:String,
    val lastName:String,
    val picture:String
)

@JsonClass(generateAdapter = true)
data class UserFullDTO(
    val dateOfBirth: String,
    val email: String,
    val firstName: String,
    val gender: String,
    val id: String,
    val lastName: String,
    val location: LocationDTO,
    val phone: String,
    val picture: String,
    val registerDate: String,
    val title: String,
    val updatedDate: String
)
// Not sure should i add generateAdapter
data class LocationDTO(
    val city: String,
    val country: String,
    val state: String,
    val street: String,
    val timezone: String
)





