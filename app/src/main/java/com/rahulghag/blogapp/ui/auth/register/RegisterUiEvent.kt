package com.rahulghag.blogapp.ui.auth.register

sealed class RegisterUiEvent {
    data class UsernameChanged(val username: String) : RegisterUiEvent()
    data class EmailChanged(val email: String) : RegisterUiEvent()
    data class PasswordChanged(val password: String) : RegisterUiEvent()
    data object Register : RegisterUiEvent()
}