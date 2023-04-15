package com.dignicate.zero_2023_android.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * https://tabris.com/how-to-trigger-navigation-in-jetpack-compose-outside-of-composables/
 */
interface NavigationDestination {
    val route: String
    val routeWithArgs: String
}

sealed class ComposeScreen(override val route: String) : NavigationDestination {

    object Main {
        class BookList : ComposeScreen(ROUTE) {
            override val routeWithArgs: String
                get() = route

            companion object {
                const val ROUTE = "book/list"
                const val ROUTE_WITH_ARGS = ROUTE
            }
        }

        class BookDetail(val bookId: Long) : ComposeScreen(ROUTE) {
            override val routeWithArgs: String
                get() = "$route/$bookId"

            companion object {
                const val ROUTE = "book/detail"
                const val ROUTE_WITH_ARGS = "$ROUTE/{id}"
            }
        }
    }

    object Unknown : ComposeScreen("unknown") {
        override val routeWithArgs: String
            get() = route
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
