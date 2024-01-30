package com.rahulghag.blogapp.domain.models

data class Author(
    val bio: String?,
    val isFollowing: Boolean,
    val image: String?,
    val username: String?
)