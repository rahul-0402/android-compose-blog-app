package com.rahulghag.blogapp.ui.articles

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rahulghag.blogapp.R
import com.rahulghag.blogapp.domain.models.Comment
import com.rahulghag.blogapp.ui.components.Author
import com.rahulghag.blogapp.ui.theme.Typography
import com.rahulghag.blogapp.ui.theme.articleTitle
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
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis,
                        style = Typography.articleTitle,
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                article.author?.let { author ->
                    Author(
                        author = author,
                        modifier = modifier
                            .padding(horizontal = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                article.description?.let {
                    Text(
                        text = it,
                        modifier = modifier
                            .padding(horizontal = 16.dp),
                        fontSize = 12.sp,
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
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
                onDismissRequest = {
                    showComments = false
                },
                sheetState = sheetState,
                comments = uiState.comments,
                isLoading = uiState.isLoading,
                onDeleteCommentClick = { id ->
                    viewModel.setEvent(ArticlesContract.Event.DeleteComment(id = id))
                }
            )
        }
    }
}

@Composable
fun Comments(
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    comments: List<Comment>,
    isLoading: Boolean,
    onDeleteCommentClick: (Int) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = {
            onDismissRequest()
        },
        sheetState = sheetState
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (isLoading) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                if (comments.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                    ) {
                        Text(
                            text = stringResource(R.string.no_comments_yet),
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = stringResource(R.string.be_the_first_to_share_your_thoughts),
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn {
                        item {
                            Text(
                                text = stringResource(id = R.string.comments),
                                modifier = Modifier
                                    .padding(horizontal = 16.dp),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        items(
                            count = comments.size,
                            key = {
                                comments[it].id
                            },
                            itemContent = { index ->
                                val comment = comments[index]
                                Comment(
                                    comment = comment,
                                    onDeleteCommentClick = { id ->
                                        onDeleteCommentClick(id)
                                    }
                                )
                                Divider()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Comment(
    comment: Comment,
    onDeleteCommentClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }

    var itemHeight by remember {
        mutableStateOf(0.dp)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val density = LocalDensity.current

    Column(
        modifier = modifier
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            }
            .indication(interactionSource, LocalIndication.current)
            .pointerInput(true) {
                detectTapGestures(
                    onLongPress = {
                        isContextMenuVisible = true
                        pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                    },
                    onPress = {
                        val press = PressInteraction.Press(it)
                        interactionSource.emit(press)
                        tryAwaitRelease()
                        interactionSource.emit(PressInteraction.Release(press))
                    }
                )
            }
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        comment.let { comment ->
            comment.author?.let {
                Author(
                    author = comment.author,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = comment.body,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp,
            )
        }
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = {
                isContextMenuVisible = false
            },
            offset = pressOffset.copy(
                y = pressOffset.y - itemHeight
            )
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(R.string.delete_comment),
                        fontSize = 12.sp
                    )
                },
                onClick = {
                    onDeleteCommentClick(comment.id)
                    isContextMenuVisible = false
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(R.string.delete_comment)
                    )
                }
            )
        }
    }
}