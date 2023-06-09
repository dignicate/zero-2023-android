package com.dignicate.zero_2023_android.ui.screen.different

import androidx.compose.foundation.layout.Box
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
import com.dignicate.zero_2023_android.ui.ComposeScreen
import timber.log.Timber

@Composable
fun DifferentNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: DifferentNavHostViewModel = hiltViewModel(),
) {
    val destination by viewModel.navigator.destination.collectAsState()
    LaunchedEffect(destination) {
        Timber.d("current: ${navController.currentDestination?.route}, path: ${destination.router.path}")
        destination.transitionIfNeeded(navController)
    }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        NavHost(
            navController,
            startDestination = DifferentScreen.Top.ROUTER.absolutePath,
        ) {
            loadDifferentNavHost(navController = navController)
        }
    }
}

object DifferentScreen {
    object Top : ComposeScreen(Router("different/top")) {
        val ROUTER = router
    }

    object Result : ComposeScreen(Router("different/result")) {
        val ROUTER = router
    }

    object Suggest : ComposeScreen(Router("different/suggest")) {
        val ROUTER = router
    }
}

fun NavGraphBuilder.loadDifferentNavHost(
    navController: NavHostController,
) {
    composable(
        route = DifferentScreen.Top.ROUTER.absolutePath,
    ) {
        DifferentTopView(
            modifier = Modifier,
            onBackClick = {
                navController.popBackStack()
            },
            onPositiveClick = {
                navController.navigate(DifferentScreen.Result.dynamicPath)
            },
            onNegativeClick = {
                navController.navigate(DifferentScreen.Suggest.dynamicPath)
            },
        )
    }
    composable(
        route = DifferentScreen.Result.ROUTER.absolutePath,
    ) {
        DifferentResultView(
            modifier = Modifier,
            onBackClick = {
                navController.popBackStack()
            },
        )
    }
    composable(
        route = DifferentScreen.Suggest.ROUTER.absolutePath,
    ) {
        DifferentSuggestView(
            modifier = Modifier,
            onBackClick = {
                navController.popBackStack()
            },
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
