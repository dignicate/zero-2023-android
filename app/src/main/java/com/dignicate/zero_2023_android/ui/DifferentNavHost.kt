package com.dignicate.zero_2023_android.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun DifferentNavHost(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: DifferentViewModel = hiltViewModel(),
) {
    val destination by viewModel.navigator.destination.collectAsState()
    val navController = rememberNavController()
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        NavHost(navController, startDestination = DifferentScreen.Top.ROUTER.absolutePath) {
            composable(
                route = DifferentScreen.Top.ROUTER.absolutePath,
            ) {
                Column(
                    modifier = modifier,
                ) {
                    Text(
                        text = "Different Top",
                    )
                    Button(
                        onClick = {
                            viewModel.navigator.navigate(DifferentScreen.Detail)
                        },
                    ) {
                        Text(
                            text = "Go To Different."
                        )
                    }
                }
            }
            composable(
                route = DifferentScreen.Detail.ROUTER.absolutePath,
            ) {
                Text(
                    text = "Different Detail",
                )
            }
            composable(
                route = ComposeScreen.Unknown.router.absolutePath,
            ) {
                Text(
                    text = "Empty",
                    modifier = modifier,
                )
            }
        }
    }
}

object DifferentScreen {
    object Top : ComposeScreen(Router("different/top")) {
        val ROUTER = router
    }

    object Detail : ComposeScreen(Router("different/detail")) {
        val ROUTER = router
    }
}
