package com.dignicate.zero_2023_android.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dignicate.zero_2023_android.domain.Book
import com.dignicate.zero_2023_android.ui.theme.Zero2023androidTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * https://developer.android.com/jetpack/compose/navigation
 * https://tabris.com/how-to-trigger-navigation-in-jetpack-compose-outside-of-composables/
 * https://stackoverflow.com/questions/67180046/how-to-inject-a-viewmodel-into-a-composable-function-using-hilt-jetpack-compose
 * https://proandroiddev.com/how-to-collect-flows-lifecycle-aware-in-jetpack-compose-babd53582d0b
 * https://medium.com/@mikhail_zhalskiy/injecting-savedstatehandle-into-viewmodel-using-dagger-2-and-jetpack-compose-30b34df9ffd1
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

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

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
}

@Composable
private fun MainScreen(
    modifier: Modifier,
    viewModel: MainViewModel,
) {
    val destination by viewModel.navigator.destination.collectAsState()
    val navController = rememberNavController()
    LaunchedEffect(destination) {
        Timber.d("current: ${navController.currentDestination?.route}, path: ${destination.router.path}")
        if (navController.currentDestination?.route != destination.router.path) {
            navController.navigate(destination.dynamicPath)
        }
    }
    Box(
        modifier = modifier,
    ) {
        NavHost(navController, startDestination = MainScreen.BookList.ROUTER.absolutePath) {
            composable(
                route = MainScreen.BookList.ROUTER.absolutePath,
            ) {
                BookListView(
                    modifier = modifier,
                    onClick = {
                        viewModel.navigator.navigate(MainScreen.BookDetail(Book.Id(it.value)))
                    },
                    onBackClicked = { navController.popBackStack() }
                )
            }
            composable(
                route = MainScreen.BookDetail.ROUTER.absolutePath,
                arguments = listOf(navArgument(MainScreen.BookDetail.ROUTER.argumentKeys[0]) {
                    type = NavType.LongType
                })
            ) {
                val bookId =
                    it.arguments!!.getLong(MainScreen.BookDetail.ROUTER.argumentKeys[0])
                BookDetailView(
                    modifier = modifier,
                    id = Book.Id(bookId),
                    onBackClicked = { navController.popBackStack() }
                )
            }
            composable(
                route = MainScreen.GoToDifferent.ROUTER.absolutePath,
            ) {
                val differentNavController = rememberNavController()
                DifferentNavHost(differentNavController)
            }
            composable(
                route = ComposeScreen.Unknown.router.absolutePath,
            ) {
                // Empty
                Text(
                    text = "Empty",
                    modifier = modifier,
                )
            }
        }
    }
}

object MainScreen {
    object BookList : ComposeScreen(Router("book/list")) {
        val ROUTER = router
    }

    class BookDetail(val bookId: Book.Id) : ComposeScreen(ROUTER) {
        override val dynamicPath: String
            get() = router.absolutePathReplacing(bookId.value)

        companion object {
            val ROUTER = Router("book/detail", "book_id")
        }
    }

    object GoToDifferent : ComposeScreen(Router("book/different")) {
        val ROUTER = router
    }
}
