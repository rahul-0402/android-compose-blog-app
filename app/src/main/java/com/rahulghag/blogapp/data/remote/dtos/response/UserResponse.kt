package com.rahulghag.blogapp.data.remote.dtos.response


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("user")
    val userDto: UserDto?
)

data class UserDto(
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("image")
    val image: Any?,
    @SerializedName("token")
    val token: String?,
    @SerializedName("username")
    val username: String?
)