package com.rahulghag.blogapp.data.remote.dtos.response


import com.google.gson.annotations.SerializedName

data class AuthorDto(
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("following")
    val following: Boolean?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("username")
    val username: String?
)