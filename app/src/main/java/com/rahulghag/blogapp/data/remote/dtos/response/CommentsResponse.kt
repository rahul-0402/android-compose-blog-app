package com.rahulghag.blogapp.data.remote.dtos.response


import com.google.gson.annotations.SerializedName


data class CommentsResponse(
    @SerializedName("comments")
    val comments: List<CommentDto>?,
)

data class CommentDto(
    @SerializedName("author")
    val authorDto: AuthorDto?,
    @SerializedName("body")
    val body: String,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("updatedAt")
    val updatedAt: String?
)