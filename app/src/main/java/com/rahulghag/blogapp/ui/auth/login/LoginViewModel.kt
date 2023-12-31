package com.rahulghag.blogapp.ui.auth.login

import androidx.lifecycle.viewModelScope
import com.rahulghag.blogapp.domain.usecases.LoginUseCase
import com.rahulghag.blogapp.ui.base.BaseViewModel
import com.rahulghag.blogapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<LoginContract.State, LoginContract.Event, LoginContract.Effect>() {
    override fun createInitialState(): LoginContract.State {
        return LoginContract.State()
    }

    override fun handleEvent(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.EmailInputChange -> {
                setState { copy(email = event.email) }
            }

            is LoginContract.Event.PasswordInputChange -> {
                setState { copy(password = event.password) }
            }

            LoginContract.Event.Login -> {
                login()
            }
        }
    }

    private fun login() = viewModelScope.launch {
        setState { copy(isLoading = true) }
        when (val result = loginUseCase.invoke(
            email = currentState.email,
            password = currentState.password
        )) {
            is Resource.Success -> {
                setState { copy(isLoading = false) }
                setEffect { LoginContract.Effect.LoginSuccess }
            }

            is Resource.Error -> {
                setState { copy(isLoading = false) }
                setEffect { LoginContract.Effect.ShowMessage(result.message) }
            }
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}