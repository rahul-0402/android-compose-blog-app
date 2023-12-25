package com.rahulghag.blogapp.data.remote.dtos.request

import com.google.gson.annotations.SerializedName

data class UserRequestDto(
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("bio")
    val bio: String? = null,
    @SerializedName("image")
    val image: String? = null
)