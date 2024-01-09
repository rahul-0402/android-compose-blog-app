package com.rahulghag.blogapp.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rahulghag.blogapp.ui.articles.ArticlesScreen
import com.rahulghag.blogapp.ui.articles.ArticlesViewModel

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    navigation(
        startDestination = Screen.Articles.name,
        route = NavGraph.HOME.name
    ) {
        composable(route = Screen.Articles.name) {
            val viewModel = hiltViewModel<ArticlesViewModel>()
            ArticlesScreen(
                viewModel = viewModel,
                snackbarHostState = snackbarHostState,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}