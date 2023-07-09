package com.dignicate.zero_2023_android.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.dignicate.zero_2023_android.domain.Book
import com.dignicate.zero_2023_android.ui.IndicatorView
import com.dignicate.zero_2023_android.ui.theme.ColorSchemeExtension.bookCell
import com.dignicate.zero_2023_android.ui.theme.ColorSchemeExtension.textMain
import com.dignicate.zero_2023_android.ui.theme.Zero2023androidTheme

@Composable
fun BookDetailView(
    modifier: Modifier = Modifier,
    id: Book.Id,
    viewModel: BookDetailViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
    onItemClicked: () -> Unit,
) {
    val lifecycleObserver = remember(viewModel) {
        LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                viewModel.onCreate(id)
            }
        }
    }
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }
    val uiState by viewModel.uiState.collectAsState()
    BookDetailView(
        modifier = modifier,
        book = uiState.book,
        showsLoadingIndicator = uiState.showsLoadingIndicator,
        errorMessage = uiState.errorMessage,
        onBackClicked = onBackClicked,
        onItemClicked = onItemClicked,
    )
}

@Composable
private fun BookDetailView(
    modifier: Modifier,
    book: BookDetailViewModel.UiState.Book?,
    showsLoadingIndicator: Boolean,
    errorMessage: String?,
    onBackClicked: () -> Unit,
    onItemClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
            BookDetailTopAppBar(
                modifier = modifier,
                onBackClicked = { onBackClicked.invoke() },
            )
        },
    ) {
        Box(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.background,
                ),
        ) {
            Column(
                modifier = Modifier,
            ) {
                Card(
                    modifier = modifier
                        .padding(horizontal = 12.dp, vertical = 12.dp)
                        .fillMaxWidth()
                        .height(140.dp),
                    elevation = CardDefaults.cardElevation(2.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.bookCell,
                                shape = RoundedCornerShape(6.dp),
                            )
                            .fillMaxSize()
                    ) {
                        Text(
                            modifier = modifier
                                .height(68.dp)
                                .padding(top = 12.dp, start = 12.dp),
                            text = book?.title ?: "",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.textMain,
                            maxLines = 2,
                        )
                        Text(
                            modifier = modifier.padding(start = 12.dp),
                            text = book?.author ?: "",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.textMain,
                        )
                        Text(
                            modifier = modifier.padding(start = 12.dp),
                            text = book?.publishedAt ?: "",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.textMain,
                        )
                    }
                }
                Text(
                    modifier = modifier.padding(start = 12.dp),
                    text = "Chapters",
                    style = MaterialTheme.typography.titleMedium,
                )
                val state = rememberLazyListState()
                LazyColumn(
                    state = state,
                ) {
                    items(book?.chapters ?: emptyList()) { item ->
                        BookDetailItemView(
                            modifier = modifier,
                            item = item,
                            onClick = {
                                onItemClicked.invoke()
                            },
                        )
                    }
                }
            }
            if (showsLoadingIndicator) {
                IndicatorView()
            }
            if (errorMessage != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
        }
    }
}

@Composable
private fun BookDetailItemView(
    modifier: Modifier,
    item: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .padding(horizontal = 12.dp, vertical = 2.dp)
            .fillMaxWidth()
            .height(60.dp)
            .clickable {
                onClick.invoke()
            },
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.bookCell,
                    shape = RoundedCornerShape(6.dp),
                ),
        ) {
            Text(
                text = item,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
            )
        }
    }
}

@Preview
@Composable
private fun BookDetailView_Preview() {
    Zero2023androidTheme {
        BookDetailView(
            modifier = Modifier,
            book = BookDetailViewModel.UiState.Book(
                title = "Title 1",
                author = "Author 1",
                publishedAt = "2023",
                chapters = listOf(
                    "Chapter I.",
                    "Chapter II.",
                    "Chapter III.",
                    "Chapter IV.",
                )
            ),
            showsLoadingIndicator = false,
            errorMessage = null,
            onBackClicked = {},
            onItemClicked = {},
        )
    }
}

@Preview
@Composable
private fun BookDetailItemView_Preview() {
    Zero2023androidTheme {
        BookDetailItemView(
            modifier = Modifier,
            item = "Chapter I.",
            onClick = {},
        )
    }
}
