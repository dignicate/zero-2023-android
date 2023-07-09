package com.dignicate.zero_2023_android.ui.screen.different

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dignicate.zero_2023_android.ui.theme.Zero2023androidTheme

@Composable
fun DifferentSuggestView(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            DifferentCommonAppBar(
                modifier = modifier,
                title = "NavHost 別定義 Suggest",
                onBackClick = onBackClick,
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "引けよ",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}

@Preview
@Composable
private fun DifferentSuggestViewPreview() {
    Zero2023androidTheme {
        DifferentSuggestView(
            modifier = Modifier,
            onBackClick = {},
        )
    }
}
