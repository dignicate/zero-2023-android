package com.dignicate.zero_2023_android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dignicate.zero_2023_android.ui.theme.ColorSchemeExtension.bgGray
import com.dignicate.zero_2023_android.ui.theme.ColorSchemeExtension.textMain
import com.dignicate.zero_2023_android.ui.theme.ColorSchemeExtension.textWhite
import com.dignicate.zero_2023_android.ui.theme.Zero2023androidTheme

@Composable
fun IndicatorView(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.bgGray)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Loading ...",
            color = MaterialTheme.colorScheme.textWhite,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IndicatorViewPreview() {
    Zero2023androidTheme {
        IndicatorView(
            modifier = Modifier,
        )
    }
}
