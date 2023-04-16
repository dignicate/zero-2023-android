package com.dignicate.zero_2023_android.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

}

@Preview(showBackground = true)
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
