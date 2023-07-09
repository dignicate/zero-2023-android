package com.dignicate.zero_2023_android.ui.screen.book

import androidx.compose.foundation.layout.Box
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
import com.dignicate.zero_2023_android.ui.ComposeScreen
import com.dignicate.zero_2023_android.ui.screen.different.DifferentScreen
import com.dignicate.zero_2023_android.ui.screen.different.loadDifferentNavHost
import timber.log.Timber

@Composable
fun BookNavHost(
    modifier: Modifier,
    viewModel: BookNavHostViewModel = hiltViewModel(),
) {
    val destination by viewModel.navigator.destination.collectAsState()
    val navController = rememberNavController()
    LaunchedEffect(destination) {
        Timber.d("current: ${navController.currentDestination?.route}, path: ${destination.router.path}")
//        if (navController.currentDestination?.route != destination.router.path) {
//            navController.navigate(destination.dynamicPath)
//        }
        destination.transitionIfNeeded(navController)
    }
    Box(
        modifier = modifier,
    ) {
        NavHost(
            navController = navController,
            startDestination = BookScreen.BookList.ROUTER.absolutePath,
        ) {
            composable(
                route = BookScreen.BookList.ROUTER.absolutePath,
            ) {
                BookListView(
                    modifier = modifier,
                    onClick = {
                        viewModel.navigator.navigate(BookScreen.BookDetail(Book.Id(it.value)))
                    },
                )
            }
            composable(
                route = BookScreen.BookDetail.ROUTER.absolutePath,
                arguments = listOf(navArgument(BookScreen.BookDetail.ROUTER.argumentKeys[0]) {
                    type = NavType.LongType
                })
            ) {
                val bookId =
                    it.arguments!!.getLong(BookScreen.BookDetail.ROUTER.argumentKeys[0])
                BookDetailView(
                    modifier = modifier,
                    id = Book.Id(bookId),
                    onBackClicked = { navController.popBackStack() },
                    onItemClicked = {
                        viewModel.navigator.navigate(DifferentScreen.Top)
                    },
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

            // 別定義の NavHost を読み込む
            loadDifferentNavHost(navController = navController)
        }
    }
}

object BookScreen {
    object BookList : ComposeScreen(Router("book/list")) {
        val ROUTER = router
    }

    class BookDetail(private val bookId: Book.Id) : ComposeScreen(ROUTER) {
        override val dynamicPath: String
            get() = router.absolutePathReplacing(bookId.value)

        companion object {
            val ROUTER = Router("book/detail", "book_id")
        }
    }
}

