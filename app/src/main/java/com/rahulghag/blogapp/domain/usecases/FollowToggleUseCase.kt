package com.rahulghag.blogapp.domain.usecases

import com.rahulghag.blogapp.domain.repositories.UserRepository
import com.rahulghag.blogapp.utils.Resource

class FollowToggleUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        isFollowing: Boolean,
        username: String
    ): Resource<Any?> {
        return if (isFollowing) {
            userRepository.unFollowUser(username = username)
        } else {
            userRepository.followUser(username = username)
        }
    }
}