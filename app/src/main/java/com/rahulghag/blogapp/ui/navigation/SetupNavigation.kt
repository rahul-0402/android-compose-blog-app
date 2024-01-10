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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rahulghag.blogapp.MainViewModel
import com.rahulghag.blogapp.ui.components.TopBar
import com.rahulghag.blogapp.ui.splash.SplashScreen

@Composable
fun SetupNavigation(
    mainViewModel: MainViewModel,
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
                title = currentScreen.title,
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
            startDestination = Screen.Splash.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screen.Splash.name) {
                SplashScreen(
                    mainViewModel = mainViewModel,
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }

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