package com.rahulghag.blogapp.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rahulghag.blogapp.ui.auth.create_account.CreateAccountScreen
import com.rahulghag.blogapp.ui.auth.create_account.CreateAccountViewModel
import com.rahulghag.blogapp.ui.auth.login.LoginScreen
import com.rahulghag.blogapp.ui.auth.login.LoginViewModel

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    navigation(
        startDestination = Screen.Login.name,
        route = NavGraph.AUTH.name
    ) {
        composable(route = Screen.Login.name) {
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                viewModel = viewModel,
                snackbarHostState = snackbarHostState,
                onNavigateToCreateAccount = {
                    navController.navigate(Screen.CreateAccount.name)
                },
                onNavigateToHome = {
                    navigateToHome(navController)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }

        composable(route = Screen.CreateAccount.name) {
            val viewModel = hiltViewModel<CreateAccountViewModel>()
            CreateAccountScreen(
                viewModel = viewModel,
                snackbarHostState = snackbarHostState,
                onNavigateToHome = {
                    navigateToHome(navController)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
    }
}

private fun navigateToHome(navController: NavHostController) {
    navController.navigate(NavGraph.HOME.name) {
        popUpTo(NavGraph.AUTH.name) {
            inclusive = true
        }
    }
}