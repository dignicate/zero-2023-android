package com.dignicate.zero_2023_android.ui

import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

/**
 * https://tabris.com/how-to-trigger-navigation-in-jetpack-compose-outside-of-composables/
 */
interface NavigationDestination {
    val router: ComposeScreen.Router
    val dynamicPath: String
        get() = router.absolutePath

    fun transitionIfNeeded(navController: NavController) {
        val isNotCurrent = navController.currentDestination?.route != router.path
        val isNotUnknown = this != ComposeScreen.Unknown
        if (isNotCurrent && isNotUnknown) {
            navController.navigate(dynamicPath)
        }
    }
}

abstract class ComposeScreen(override val router: Router) : NavigationDestination {

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

        fun absolutePathReplacing(vararg values: Any): String {
            val stringBuilder = StringBuilder(path)
            values.forEach {
                stringBuilder.append("/$it")
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
        Timber.d("navigate() destination: $destination")
        this._destination.value = destination
    }
}
