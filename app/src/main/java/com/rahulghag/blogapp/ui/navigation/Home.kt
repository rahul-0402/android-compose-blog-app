package com.rahulghag.blogapp.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rahulghag.blogapp.ui.articles.ArticlesViewModel
import com.rahulghag.blogapp.ui.articles.screens.AddCommentScreen
import com.rahulghag.blogapp.ui.articles.screens.ArticleDetailsScreen
import com.rahulghag.blogapp.ui.articles.screens.ArticlesScreen
import com.rahulghag.blogapp.utils.sharedViewModel

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    navigation(
        startDestination = Screen.Articles.name,
        route = NavGraph.HOME.name
    ) {
        composable(route = Screen.Articles.name) {
            val viewModel = it.sharedViewModel<ArticlesViewModel>(navController)
            ArticlesScreen(
                viewModel = viewModel,
                snackbarHostState = snackbarHostState,
                onNavigateToArticleDetails = {
                    navController.navigate(Screen.ArticleDetails.name)
                },
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        composable(route = Screen.ArticleDetails.name) {
            val viewModel = it.sharedViewModel<ArticlesViewModel>(navController)
            ArticleDetailsScreen(
                viewModel = viewModel,
                snackbarHostState = snackbarHostState,
                onNavigateToAddComment = {
                    navController.navigate(Screen.AddComment.name)
                },
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        composable(route = Screen.AddComment.name) {
            val viewModel = it.sharedViewModel<ArticlesViewModel>(navController)
            AddCommentScreen(
                viewModel = viewModel,
                snackbarHostState = snackbarHostState,
                onNavigateToArticleDetails = {
                    navController.navigate(Screen.ArticleDetails.name) {
                        popUpTo(Screen.ArticleDetails.name) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}