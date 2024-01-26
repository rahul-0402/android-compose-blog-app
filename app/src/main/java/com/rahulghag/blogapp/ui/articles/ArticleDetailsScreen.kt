package com.rahulghag.blogapp.ui.articles

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.rahulghag.blogapp.domain.models.Author
import com.rahulghag.blogapp.domain.models.Comment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ArticleDetailsScreen(
    viewModel: ArticlesViewModel,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState()

    var showComments by remember { mutableStateOf(false) }

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

                else -> {

                }
            }
        }
    }

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
        ) {
            uiState.selectedArticle?.let { article ->
                article.title?.let {
                    Text(
                        text = it,
                        modifier = modifier
                            .padding(start = 16.dp, top = 12.dp, end = 16.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Author(
                    author = article.author,
                    modifier = modifier
                        .padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                article.description?.let {
                    Text(
                        text = it,
                        modifier = modifier
                            .padding(horizontal = 16.dp),
                        fontSize = 12.sp,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        ExtendedFloatingActionButton(
            text = {
                Text(stringResource(R.string.comments))
            },
            icon = {
                Icon(
                    Icons.Filled.ThumbUp,
                    contentDescription = stringResource(R.string.comments)
                )
            },
            onClick = {
                showComments = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )

        if (showComments) {
            Comments(
                onDismissRequest = { showComments = false },
                sheetState = sheetState,
                comments = uiState.comments
            )
        }
    }
}

@Composable
private fun Author(
    author: Author?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        author?.username?.let {
            Text(
                text = it,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun Comments(onDismissRequest: () -> Unit, sheetState: SheetState, comments: List<Comment>) {
    ModalBottomSheet(
        onDismissRequest = {
            onDismissRequest()
        },
        sheetState = sheetState
    ) {
        LazyColumn {
            items(
                count = comments.size,
                key = {
                    comments[it].id
                },
                itemContent = { index ->
                    val comment = comments[index]
                    Comment(comment = comment)
                }
            )
        }
    }
}

@Composable
fun Comment(
    comment: Comment,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        Author(author = comment.author)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = comment.body,
            modifier = modifier,
            fontSize = 12.sp,
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}