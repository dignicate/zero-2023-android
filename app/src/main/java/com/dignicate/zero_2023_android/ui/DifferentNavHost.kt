package com.dignicate.zero_2023_android.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import timber.log.Timber

@Composable
fun DifferentNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: DifferentViewModel = hiltViewModel(),
) {
    val destination by viewModel.navigator.destination.collectAsState()
    LaunchedEffect(destination) {
        Timber.d("current: ${navController.currentDestination?.route}, path: ${destination.router.path}")
        if (navController.currentDestination?.route != destination.router.path) {
            navController.navigate(destination.dynamicPath)
        }
    }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        NavHost(navController, startDestination = DifferentScreen.Top.ROUTER.absolutePath) {
            loadDifferentNavHost(navController = navController)
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

    val route = "different"
}

fun NavGraphBuilder.loadDifferentNavHost(
    navController: NavHostController,
) {
    navigation(
        startDestination = DifferentScreen.Top.ROUTER.absolutePath,
        route = DifferentScreen.route,
    ) {
        composable(
            route = DifferentScreen.Top.ROUTER.absolutePath,
        ) {
            Column(
            ) {
                Text(
                    text = "Different Top",
                )
                Button(
                    onClick = {
                        navController.navigate(DifferentScreen.Detail.dynamicPath)
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
                modifier = Modifier,
            )
        }
    }
}
