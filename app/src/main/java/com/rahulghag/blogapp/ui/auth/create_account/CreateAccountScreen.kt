package com.rahulghag.blogapp.ui.auth.create_account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rahulghag.blogapp.R
import com.rahulghag.blogapp.ui.components.TextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun CreateAccountScreen(
    viewModel: CreateAccountViewModel,
    snackbarHostState: SnackbarHostState,
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is CreateAccountContract.Effect.ShowMessage -> {
                    effect.uiMessage?.let {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(it.asString(context))
                        }
                    }
                }

                CreateAccountContract.Effect.AccountCreationSuccess -> {
                    onNavigateToHome()
                }
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = uiState.username,
                onValueChange = {
                    viewModel.setEvent(CreateAccountContract.Event.UsernameInputChange(it))
                },
                label = { Text(stringResource(R.string.enter_username)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            TextField(
                value = uiState.email,
                onValueChange = { viewModel.setEvent(CreateAccountContract.Event.EmailInputChange(it)) },
                label = { Text(stringResource(R.string.enter_email)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            TextField(
                value = uiState.password,
                onValueChange = {
                    viewModel.setEvent(CreateAccountContract.Event.PasswordInputChange(it))
                },
                label = { Text(stringResource(R.string.enter_password)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Button(
                onClick = { viewModel.setEvent(CreateAccountContract.Event.CreateAccount) },
            ) {
                Text(text = stringResource(R.string.submit))
            }
        }
    }
}