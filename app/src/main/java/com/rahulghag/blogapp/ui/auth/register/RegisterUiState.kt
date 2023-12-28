package com.rahulghag.blogapp.ui.auth.register

import com.rahulghag.blogapp.utils.UiMessage

data class RegisterUiState(
    var username: String = "",
    var email: String = "",
    var password: String = "",
    val isLoading: Boolean = false,
    val message: UiMessage? = null
)