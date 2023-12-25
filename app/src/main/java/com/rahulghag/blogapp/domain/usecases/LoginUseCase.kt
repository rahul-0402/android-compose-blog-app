package com.rahulghag.blogapp.domain.usecases

import com.rahulghag.blogapp.domain.models.User
import com.rahulghag.blogapp.domain.repositories.AuthRepository
import com.rahulghag.blogapp.utils.Resource

class LoginUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): Resource<User> {
        return authRepository.login(email = email, password = password)
    }
}