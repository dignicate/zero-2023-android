package com.dignicate.zero_2023_android.ui.screen.book

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.dignicate.zero_2023_android.ui.theme.Zero2023androidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListTopAppBar(
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = "Book List",
                modifier = modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
            )
        },
    )
}

@Preview
@Composable
fun BookListTopAppBar_Preview() {
    Zero2023androidTheme {
        BookListTopAppBar(
            modifier = Modifier,
        )
    }
}
