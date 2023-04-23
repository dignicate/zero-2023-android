package com.dignicate.zero_2023_android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.dignicate.zero_2023_android.domain.Book
import com.dignicate.zero_2023_android.ui.theme.ColorSchemeExtension.bookCell
import com.dignicate.zero_2023_android.ui.theme.Zero2023androidTheme

@Composable
fun BookDetailView(
    id: Book.Id,
    modifier: Modifier = Modifier,
    viewModel: BookDetailViewModel = hiltViewModel(),
) {
    val lifecycleObserver = remember(viewModel) {
        LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                viewModel.onCreate()
            } else if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.onResume(id)
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
        data = uiState.data,
    )
}

@Composable
private fun BookDetailView(
    modifier: Modifier,
    data: BookDetailViewModel.Data?,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background,
            )
        ,
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = 12.dp, vertical = 12.dp)
                .background(
                    color = MaterialTheme.colorScheme.bookCell,
                    shape = RoundedCornerShape(6.dp),
                )
                .fillMaxWidth()
                .height(140.dp)
        ,
        ) {
            Text(
                modifier = modifier.padding(top = 12.dp, start = 12.dp),
                text = data?.title ?: "",
                style = MaterialTheme.typography.titleLarge,
            )
        }
        Text(
            modifier = modifier.padding(start = 12.dp),
            text = "Chapters",
            style = MaterialTheme.typography.titleMedium,
        )
        val state = rememberLazyListState()
        LazyColumn {
            items(data?.chapters ?: emptyList()) {
                BookDetailItemView(
                    modifier = modifier,
                    item = it,
                )
            }
        }
    }
}

@Composable
private fun BookDetailItemView(
    modifier: Modifier,
    item: String,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(horizontal = 12.dp, vertical = 2.dp)
            .fillMaxWidth()
            .height(40.dp)
            .background(
                color = MaterialTheme.colorScheme.bookCell,
                shape = RoundedCornerShape(6.dp),
            ),
    ) {
        Text(
            text = item,
            modifier = modifier
                .padding(horizontal = 12.dp)
        )
    }
}

@Preview
@Composable
private fun BookDetailView_Preview() {
    Zero2023androidTheme {
        BookDetailView(
            modifier = Modifier,
            data = BookDetailViewModel.Data(
                title = "Title 1",
                author = "Author 1",
                publishedAt = "2023",
                chapters = listOf(
                    "Chapter I.",
                    "Chapter II.",
                )
            )
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
        )
    }
}
