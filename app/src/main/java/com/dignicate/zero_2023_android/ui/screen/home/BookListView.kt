package com.dignicate.zero_2023_android.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.dignicate.zero_2023_android.ui.IndicatorView
import com.dignicate.zero_2023_android.ui.theme.ColorSchemeExtension.bookCell
import com.dignicate.zero_2023_android.ui.theme.ColorSchemeExtension.textMain
import com.dignicate.zero_2023_android.ui.theme.TypographyExtension.bodyTitle
import com.dignicate.zero_2023_android.ui.theme.Zero2023androidTheme

@Composable
fun BookListView(
    modifier: Modifier = Modifier,
    viewModel: BookListViewModel = hiltViewModel(),
    onClick: (BookListViewModel.UiState.BookSummary.Id) -> Unit,
) {
    val lifecycleObserver = remember(viewModel) {
        LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                viewModel.onCreate()
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
    BookListView(
        modifier = modifier,
        books = uiState.books,
        showsLoadingIndicator = uiState.showsLoadingIndicator,
        onClick = { onClick.invoke(it) },
    )
}

@Composable
private fun BookListView(
    modifier: Modifier,
    books: List<BookListViewModel.UiState.BookSummary>,
    showsLoadingIndicator: Boolean,
    onClick: (BookListViewModel.UiState.BookSummary.Id) -> Unit,
) {
    val state = rememberLazyGridState()
    Scaffold(
        topBar = {
            BookListTopAppBar(
                modifier = modifier,
            )
        }
    ) {
        Box(
            modifier = modifier
                .padding(it)
                .background(
                    color = MaterialTheme.colorScheme.background,
                ),
        ) {
            Column(
                modifier = Modifier,
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(159.dp),
                    state = state,
                    modifier = modifier
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(books.size) { index ->
                        val item = books[index]
                        BookListItemView(
                            modifier = modifier,
                            item = item,
                            onClick = { id ->
                                onClick.invoke(id)
                            }
                        )
                    }
                }
            }
            if (showsLoadingIndicator) {
                IndicatorView()
            }
        }
    }
}

@Composable
private fun BookListItemView(
    modifier: Modifier,
    item: BookListViewModel.UiState.BookSummary,
    onClick: (BookListViewModel.UiState.BookSummary.Id) -> Unit,
) {
    Box(
        modifier
            .width(159.dp)
            .height(209.dp)
            .background(
                color = MaterialTheme.colorScheme.bookCell,
                shape = RoundedCornerShape(6.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                onClick.invoke(item.id)
            }
    ,
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = 24.dp, vertical = 24.dp)
            ,
        ) {
            Text(
                text = item.title,
                color = MaterialTheme.colorScheme.textMain,
                style=  MaterialTheme.typography.bodyTitle,
                modifier = modifier
                    .height(36.dp),
                maxLines = 2,
            )
            Spacer(modifier = modifier.height(8.dp))
            Text(
                text = item.author,
                color = MaterialTheme.colorScheme.textMain,
                style = MaterialTheme.typography.labelSmall,
                modifier = modifier
                    .height(28.dp),
                maxLines = 2,
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun BookListView_Preview() {
    Zero2023androidTheme {
        BookListView(
            modifier = Modifier,
            books = listOf(
                BookListViewModel.UiState.BookSummary(
                    id = BookListViewModel.UiState.BookSummary.Id(1L),
                    title = "Title 1",
                    author = "Author 1",
                ),
                BookListViewModel.UiState.BookSummary(
                    id = BookListViewModel.UiState.BookSummary.Id(2L),
                    title = "Title 2",
                    author = "Author 2",
                )
            ),
            showsLoadingIndicator = false,
            onClick = {},
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun BookListItemView_Preview() {
    Zero2023androidTheme {
        BookListItemView(
            modifier = Modifier,
            item = BookListViewModel.UiState.BookSummary(
                id = BookListViewModel.UiState.BookSummary.Id(1L),
                title = "The War of the Words",
                author = "Author",
            ),
            onClick = {},
        )
    }
}
