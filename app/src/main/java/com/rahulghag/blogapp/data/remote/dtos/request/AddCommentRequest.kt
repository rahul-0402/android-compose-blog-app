package com.rahulghag.blogapp.data.remote.dtos.request


import com.google.gson.annotations.SerializedName

data class AddCommentRequest(
    @SerializedName("comment")
    val comment: CommentDto
)

data class CommentDto(
    @SerializedName("body")
    val body: String
)