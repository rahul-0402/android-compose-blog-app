package com.rahulghag.blogapp.ui.auth.login

import com.rahulghag.blogapp.utils.UiMessage

data class LoginUiState(
    var email: String = "",
    var password: String = "",
    val isLoading: Boolean = false,
    val message: UiMessage? = null
)