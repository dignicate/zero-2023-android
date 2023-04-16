package com.dignicate.zero_2023_android.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dignicate.zero_2023_android.ui.theme.Zero2023androidTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * https://developer.android.com/jetpack/compose/navigation
 * https://tabris.com/how-to-trigger-navigation-in-jetpack-compose-outside-of-composables/
 * https://stackoverflow.com/questions/67180046/how-to-inject-a-viewmodel-into-a-composable-function-using-hilt-jetpack-compose
 * https://proandroiddev.com/how-to-collect-flows-lifecycle-aware-in-jetpack-compose-babd53582d0b
 * https://medium.com/@mikhail_zhalskiy/injecting-savedstatehandle-into-viewmodel-using-dagger-2-and-jetpack-compose-30b34df9ffd1
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Zero2023androidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        modifier = Modifier,
                        viewModel = hiltViewModel(),
                    )
                }
            }
        }
    }
}

@Composable
private fun MainScreen(
    modifier: Modifier,
    viewModel: MainViewModel,
) {

}

@Composable
private fun MainScreen(
    modifier: Modifier,
    destination: NavigationDestination,
) {
    val navController = rememberNavController()
    LaunchedEffect(destination) {
        if (navController.currentDestination?.route != destination.router.path) {
            navController.navigate(destination.router.absolutePath)
        }
    }
    Box(
        modifier = modifier,
    ) {
        NavHost(navController, startDestination = ComposeScreen.Unknown.router.absolutePath) {
            composable(
                route = ComposeScreen.Main.BookList.ROUTER.absolutePath,
            ) {
                // TODO:
            }
            composable(
                route = ComposeScreen.Main.BookDetail.ROUTER.absolutePath,
                arguments = listOf(navArgument(ComposeScreen.Main.BookDetail.ROUTER.argumentKeys[0]) { type = NavType.LongType })
            ) {
                val bookId = it.arguments!!.getLong(ComposeScreen.Main.BookDetail.ROUTER.argumentKeys[0])
                // TODO:
            }
            composable(
                route = ComposeScreen.Unknown.router.absolutePath,
            ) {
                // Empty
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun MainScreen_Preview() {
//    Zero2023androidTheme {
//
//    }
//}
