package com.example.android_architecture_example.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserFull(
    val dateOfBirth: String,
    val email: String,
    val firstName: String,
    val gender: String,
    val id: String,
    val lastName: String,
    val location: Location,
    val phone: String,
    val picture: String,
    val registerDate: String,
    val title: String,
    val updatedDate: String
) : Parcelable

// IDK if it will work or not. TO-DO
@Parcelize
data class Location(
    val city: String,
    val country: String,
    val state: String,
    val street: String,
    val timezone: String
) : Parcelable