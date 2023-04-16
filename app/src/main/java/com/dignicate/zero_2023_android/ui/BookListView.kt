package com.dignicate.zero_2023_android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dignicate.zero_2023_android.ui.theme.Zero2023androidTheme

@Composable
fun BookListView(
    modifier: Modifier = Modifier,
    viewModel: BookListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    BookListView(
        modifier = modifier,
        data = uiState.data,
    )
}

@Composable
private fun BookListView(
    modifier: Modifier,
    data: BookListViewModel.Data?,
) {
    val state = rememberLazyGridState()
    LazyVerticalGrid(
        columns = GridCells.Adaptive(90.dp),
        state = state,
    ) {
        val items = data?.items ?: emptyList()
        items(items.size) { index ->
            val item = items[index]
            BookListItemView(
                modifier = modifier,
                item = item,
            )
        }
    }
}

@Composable
private fun BookListItemView(
    modifier: Modifier,
    item: BookListViewModel.Data.Item,
) {
    Box(
        modifier
            .width(90.dp)
            .height(128.dp)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(6.dp)
            )
    ) {
        Column(
            modifier = modifier,
        ) {
            Text(
                text = item.title,
                color = MaterialTheme.colorScheme.primary,
                style=  MaterialTheme.typography.titleMedium,
                modifier = modifier,
            )
            Text(
                text = item.author,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelSmall,
                modifier = modifier,
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
            data = BookListViewModel.Data(
                items = listOf(
                    BookListViewModel.Data.Item(
                        title = "Title 1",
                        author = "",
                    ),
                    BookListViewModel.Data.Item(
                        title = "Title 2",
                        author = "",
                    )
                )
            )
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun BookListItemView_Preview() {
    Zero2023androidTheme {
        BookListItemView(
            modifier = Modifier,
            item = BookListViewModel.Data.Item(
                title = "Title",
                author = "Author",
            )
        )
    }
}
