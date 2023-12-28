package com.rahulghag.blogapp.domain.repositories

import com.rahulghag.blogapp.domain.models.User
import com.rahulghag.blogapp.utils.Resource

interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<User>

    suspend fun register(username: String, email: String, password: String): Resource<User>
}