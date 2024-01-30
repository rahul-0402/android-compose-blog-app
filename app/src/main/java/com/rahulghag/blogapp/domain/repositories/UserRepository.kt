package com.rahulghag.blogapp.domain.repositories

import com.rahulghag.blogapp.utils.Resource

interface UserRepository {
    suspend fun followUser(username: String): Resource<Any?>
    suspend fun unFollowUser(username: String): Resource<Any?>
}