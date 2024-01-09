package com.rahulghag.blogapp.ui.articles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rahulghag.blogapp.R
import com.rahulghag.blogapp.domain.models.Article
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ArticlesScreen(
    viewModel: ArticlesViewModel,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ArticlesContract.Effect.ShowMessage -> {
                    effect.uiMessage?.let {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(it.asString(context))
                        }
                    }
                }
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        if (uiState.items.isEmpty() && uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            Articles(
                articles = uiState.items,
                lastPageReached = uiState.lastPageReached,
                isLoading = uiState.isLoading,
                onLoadNextItems = {
                    viewModel.getArticles()
                }
            )
        }
    }
}

@Composable
private fun Articles(
    articles: List<Article>,
    lastPageReached: Boolean,
    isLoading: Boolean,
    onLoadNextItems: () -> Unit
) {
    val scrollState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        items(
            count = articles.size
        ) { i ->
            val article = articles[i]
            LaunchedEffect(scrollState) {
                if (i >= articles.size - 1 && !lastPageReached && !isLoading) {
                    onLoadNextItems()
                }
            }
            ArticleItem(article)
            Divider()
        }
        item {
            if (isLoading) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun ArticleItem(article: Article) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 16.dp)
    ) {
        article.title?.let {
            Text(
                text = it,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        article.author?.username?.let {
            Text(
                text = stringResource(R.string.by, it),
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        article.createdAt?.let {
            Text(
                text = stringResource(R.string.published_on, it),
                fontSize = 12.sp
            )
        }
    }
}