package com.rahulghag.blogapp.ui.auth.login

import com.rahulghag.blogapp.utils.UiMessage

data class LoginUiState(
    val isLoading: Boolean = false,
    val message: UiMessage? = null
)