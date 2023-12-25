package com.rahulghag.blogapp.ui.auth.login

sealed class LoginUiEvent {
    data object Login : LoginUiEvent()
}