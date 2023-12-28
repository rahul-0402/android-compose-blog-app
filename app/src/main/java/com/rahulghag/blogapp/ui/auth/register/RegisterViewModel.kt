package com.rahulghag.blogapp.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulghag.blogapp.domain.usecases.RegisterUseCase
import com.rahulghag.blogapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onEvent(event: RegisterUiEvent) {
        when (event) {
            is RegisterUiEvent.UsernameChanged -> {
                _uiState.update { it.copy(username = event.username) }
            }

            is RegisterUiEvent.EmailChanged -> {
                _uiState.update { it.copy(email = event.email) }
            }

            is RegisterUiEvent.PasswordChanged -> {
                _uiState.update { it.copy(password = event.password) }
            }

            RegisterUiEvent.Register -> {
                register()
            }
        }
    }

    private fun register() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        when (val result = registerUseCase.invoke(
            username = uiState.value.username,
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