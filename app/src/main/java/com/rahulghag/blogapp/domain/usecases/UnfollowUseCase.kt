package com.rahulghag.blogapp.domain.usecases

import com.rahulghag.blogapp.domain.repositories.UserRepository
import com.rahulghag.blogapp.utils.Resource

class UnfollowUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        username: String
    ): Resource<Any?> {
        return userRepository.unfollowUser(username = username)
    }
}