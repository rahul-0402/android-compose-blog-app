package com.rahulghag.blogapp.domain.usecases

import com.rahulghag.blogapp.R
import com.rahulghag.blogapp.domain.models.User
import com.rahulghag.blogapp.domain.repositories.AuthRepository
import com.rahulghag.blogapp.utils.Resource
import com.rahulghag.blogapp.utils.UiMessage
import com.rahulghag.blogapp.utils.isValidEmail

class LoginUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): Resource<User> {
        if (email.isEmpty()) {
            return Resource.Error(message = UiMessage.StringResource(R.string.email_cannot_be_blank))
        }

        if (!email.isValidEmail()) {
            return Resource.Error(message = UiMessage.StringResource(R.string.invalid_email_format))
        }

        if (password.isEmpty()) {
            return Resource.Error(message = UiMessage.StringResource(R.string.password_cannot_be_blank))
        }

        return authRepository.login(email = email, password = password)
    }
}