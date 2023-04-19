package com.dignicate.zero_2023_android.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * https://tabris.com/how-to-trigger-navigation-in-jetpack-compose-outside-of-composables/
 */
interface NavigationDestination {
    val router: ComposeScreen.Router
}

sealed class ComposeScreen(override val router: Router) : NavigationDestination {

    object Main {
        object BookList : ComposeScreen(Router("book/list")) {
            val ROUTER = router
        }

        class BookDetail(val bookId: Long) : ComposeScreen(ROUTER) {
            companion object {
                val ROUTER = Router("book/detail", "book_id")
            }
        }
    }

    object Unknown : ComposeScreen(Router("unknown"))

    class Router(
        val path: String,
        vararg val argumentKeys: String,
    ) {
        val absolutePath: String
            get() {
                val stringBuilder = StringBuilder(path)
                argumentKeys.forEach {
                    stringBuilder.append("/{$it}")
                }
                return stringBuilder.toString()
            }
    }
}

class ComposeNavigator {
    private val _destination: MutableStateFlow<NavigationDestination> = MutableStateFlow(ComposeScreen.Unknown)

    val destination: StateFlow<NavigationDestination>
        get() = _destination.asStateFlow()

    fun navigate(destination: NavigationDestination) {
        this._destination.value = destination
    }
}
