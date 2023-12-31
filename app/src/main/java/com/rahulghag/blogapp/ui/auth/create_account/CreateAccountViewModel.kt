package com.rahulghag.blogapp.ui.auth.create_account

import androidx.lifecycle.viewModelScope
import com.rahulghag.blogapp.domain.usecases.CreateAccountUseCase
import com.rahulghag.blogapp.ui.base.BaseViewModel
import com.rahulghag.blogapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val createAccountUseCase: CreateAccountUseCase,
) : BaseViewModel<CreateAccountContract.State, CreateAccountContract.Event, CreateAccountContract.Effect>() {
    override fun createInitialState() = CreateAccountContract.State()

    override fun handleEvent(event: CreateAccountContract.Event) {
        when (event) {
            is CreateAccountContract.Event.UsernameInputChange -> {
                setState { copy(username = event.username) }
            }

            is CreateAccountContract.Event.EmailInputChange -> {
                setState { copy(email = event.email) }
            }

            is CreateAccountContract.Event.PasswordInputChange -> {
                setState { copy(password = event.password) }
            }

            CreateAccountContract.Event.CreateAccount -> {
                createAccount()
            }
        }
    }

    private fun createAccount() = viewModelScope.launch {
        setState { copy(isLoading = true) }
        when (val result = createAccountUseCase.invoke(
            username = currentState.username,
            email = currentState.email,
            password = currentState.password
        )) {
            is Resource.Success -> {
                setState { copy(isLoading = false) }
                setEffect { CreateAccountContract.Effect.AccountCreationSuccess }
            }

            is Resource.Error -> {
                setState { copy(isLoading = false) }
                setEffect { CreateAccountContract.Effect.ShowMessage(result.message) }
            }
        }
    }
}