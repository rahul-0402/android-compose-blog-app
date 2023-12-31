package com.rahulghag.blogapp.ui.auth.login

import com.rahulghag.blogapp.ui.base.UiEffect
import com.rahulghag.blogapp.ui.base.UiEvent
import com.rahulghag.blogapp.ui.base.UiState
import com.rahulghag.blogapp.utils.UiMessage

class LoginContract {
    data class State(
        val email: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
    ) : UiState

    sealed class Event : UiEvent {
        data class EmailInputChange(val email: String) : Event()
        data class PasswordInputChange(val password: String) : Event()
        data object Login : Event()
    }

    sealed class Effect : UiEffect {
        data object LoginSuccess : Effect()
        data class ShowMessage(val uiMessage: UiMessage?) : Effect()
    }
}