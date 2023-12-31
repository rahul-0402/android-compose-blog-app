package com.rahulghag.blogapp.ui.auth.create_account

import com.rahulghag.blogapp.ui.base.UiEffect
import com.rahulghag.blogapp.ui.base.UiEvent
import com.rahulghag.blogapp.ui.base.UiState
import com.rahulghag.blogapp.utils.UiMessage

class CreateAccountContract {
    data class State(
        val username: String = "",
        val email: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
    ) : UiState

    sealed class Event : UiEvent {
        data class UsernameInputChange(val username: String) : Event()
        data class EmailInputChange(val email: String) : Event()
        data class PasswordInputChange(val password: String) : Event()
        data object CreateAccount : Event()
    }

    sealed class Effect : UiEffect {
        data object AccountCreationSuccess : Effect()
        data class ShowMessage(val uiMessage: UiMessage?) : Effect()
    }
}