package com.rahulghag.blogapp.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulghag.blogapp.domain.usecases.LoginUseCase
import com.rahulghag.blogapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailChanged -> {
                _uiState.update { it.copy(email = event.email) }
            }

            is LoginUiEvent.PasswordChanged -> {
                _uiState.update { it.copy(password = event.password) }
            }

            LoginUiEvent.Login -> {
                login()
            }
        }
    }

    private fun login() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        when (val result = loginUseCase.invoke(
            email = uiState.value.email,
            password = uiState.value.password
        )) {
            is Resource.Success -> {
                _uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }

            is Resource.Error -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        message = result.message
                    )
                }
            }
        }
    }

    fun messageShown() {
        _uiState.update { it.copy(message = null) }
    }
}