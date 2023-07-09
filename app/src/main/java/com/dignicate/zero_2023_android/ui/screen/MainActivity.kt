package com.dignicate.zero_2023_android.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.dignicate.zero_2023_android.ui.screen.book.BookNavHost
import com.dignicate.zero_2023_android.ui.screen.book.BookNavHostViewModel
import com.dignicate.zero_2023_android.ui.screen.different.DifferentNavHost
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
                    val navController = rememberNavController()
                    val scenario = Scenario.CASE1
                    when (scenario) {
                        Scenario.CASE1 -> {
                            // BookNavHost から始まり、その先にある子画面遷移として DifferentNavHost につながる
                            BookNavHost(
                                modifier = Modifier,
                                navController = navController,
                            )
                        }
                        Scenario.CASE2 -> {
                            // 子の画面遷移を単独の定義として Top として表示する
                            DifferentNavHost(
                                modifier = Modifier,
                                navController = navController,
                    )
                        }
                    }
                }
            }
        }
    }
}

enum class Scenario {
    CASE1, CASE2
}
