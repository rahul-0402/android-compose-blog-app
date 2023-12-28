package com.rahulghag.blogapp.domain.usecases

import com.rahulghag.blogapp.R
import com.rahulghag.blogapp.domain.models.User
import com.rahulghag.blogapp.domain.repositories.AuthRepository
import com.rahulghag.blogapp.utils.Resource
import com.rahulghag.blogapp.utils.UiMessage
import com.rahulghag.blogapp.utils.isValidEmail

class RegisterUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(username: String, email: String, password: String): Resource<User> {
        if (username.isEmpty()) {
            return Resource.Error(message = UiMessage.StringResource(R.string.username_cannot_be_blank))
        }

        if (email.isEmpty()) {
            return Resource.Error(message = UiMessage.StringResource(R.string.email_cannot_be_blank))
        }

        if (!email.isValidEmail()) {
            return Resource.Error(message = UiMessage.StringResource(R.string.invalid_email_format))
        }

        if (password.isEmpty()) {
            return Resource.Error(message = UiMessage.StringResource(R.string.password_cannot_be_blank))
        }

        if (password.length < MIN_PASSWORD_LENGTH) {
            return Resource.Error(message = UiMessage.StringResource(R.string.password_too_short))
        }

        return authRepository.register(username = username, email = email, password = password)
    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 6
    }
}