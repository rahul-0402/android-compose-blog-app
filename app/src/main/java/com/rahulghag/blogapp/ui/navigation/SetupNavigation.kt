package com.rahulghag.blogapp.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.rahulghag.blogapp.ui.auth.login.LoginScreen
import com.rahulghag.blogapp.ui.auth.login.LoginViewModel
import com.rahulghag.blogapp.ui.auth.create_account.CreateAccountScreen
import com.rahulghag.blogapp.ui.auth.create_account.CreateAccountViewModel
import com.rahulghag.blogapp.ui.components.TopBar

@Composable
fun SetupNavigation(
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()

    // Get the name of the current screen
    val currentScreen = Screen.valueOf(
        backStackEntry?.destination?.route ?: Screen.Login.name
    )

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavGraph.AUTH.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            authGraph(
                navController = navController,
                snackbarHostState = snackbarHostState
            )
            homeGraph(
                navController = navController,
                snackbarHostState = snackbarHostState
            )
        }
    }
}

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

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    navigation(
        startDestination = Screen.Articles.name,
        route = NavGraph.HOME.name
    ) {
        composable(route = Screen.Articles.name) {

        }
    }
}