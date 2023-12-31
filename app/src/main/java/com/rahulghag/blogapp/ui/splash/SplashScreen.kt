package com.rahulghag.blogapp.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.rahulghag.blogapp.MainViewModel
import com.rahulghag.blogapp.ui.navigation.NavGraph
import com.rahulghag.blogapp.ui.navigation.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    mainViewModel: MainViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(key1 = Unit) {
        mainViewModel.isUserLoggedIn.collectLatest { isUserLoggedIn ->
            navController.navigate(determineStartDestination(isUserLoggedIn = isUserLoggedIn)) {
                popUpTo(Screen.Splash.name) {
                    inclusive = true
                }
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {

    }
}

private fun determineStartDestination(isUserLoggedIn: Boolean): String {
    return if (isUserLoggedIn) NavGraph.HOME.name else NavGraph.AUTH.name
}