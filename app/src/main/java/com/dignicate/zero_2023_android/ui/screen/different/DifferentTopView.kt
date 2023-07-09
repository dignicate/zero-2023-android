package com.dignicate.zero_2023_android.ui.screen.different

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import com.dignicate.zero_2023_android.ui.theme.ColorSchemeExtension.bgButton
import com.dignicate.zero_2023_android.ui.theme.ColorSchemeExtension.textWhite
import com.dignicate.zero_2023_android.ui.theme.Zero2023androidTheme

@Composable
fun DifferentTopView(
    modifier: Modifier = Modifier,
    viewModel: DifferentTopViewModel = hiltViewModel(),
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit,
    onBackClick: () -> Unit,
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
    DifferentTopView(
        modifier = modifier,
        value = uiState.value,
        onPositiveClick = onPositiveClick,
        onNegativeClick = onNegativeClick,
        onBackClick = onBackClick,
    )
}

@Composable
private fun DifferentTopView(
    modifier: Modifier,
    value: String?,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            DifferentCommonAppBar(
                modifier = modifier,
                title = "NavHost 別定義 Top",
                onBackClick = onBackClick,
            )
        }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = modifier,
            ) {
                ButtonPart(
                    text = "ガチャを引く",
                    onClick = {
                        onPositiveClick.invoke()
                    },
                )
                Spacer(Modifier.height(32.dp))
                ButtonPart(
                    text = "ガチャを引かない",
                    onClick = {
                        onNegativeClick.invoke()
                    },
                )
            }
        }
    }
}

@Composable
private fun ButtonPart(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .width(240.dp)
            .height(48.dp)
            .clickable {
                onClick.invoke()
            },
        elevation = CardDefaults.cardElevation(3.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.bgButton,
                    shape = RoundedCornerShape(8.dp),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.textWhite,
            )
        }
    }
}

@Preview
@Composable
private fun DifferentTopViewPreview() {
    Zero2023androidTheme {
        DifferentTopView(
            modifier = Modifier,
            value = null,
            onPositiveClick = {},
            onNegativeClick = {},
            onBackClick = {},
        )
    }
}
