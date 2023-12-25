package com.rahulghag.blogapp.data.remote.dtos.request

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("user")
    val userRequestDto: UserRequestDto? = null
)