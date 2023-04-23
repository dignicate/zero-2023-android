package com.dignicate.zero_2023_android.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dignicate.zero_2023_android.ui.theme.ColorSchemeExtension.textMain
import com.dignicate.zero_2023_android.ui.theme.Zero2023androidTheme

@Composable
fun InProgressView(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Loading ...",
            color = MaterialTheme.colorScheme.textMain,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InProgressView_Preview() {
    Zero2023androidTheme {
        InProgressView(
            modifier = Modifier,
        )
    }
}
