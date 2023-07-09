package com.dignicate.zero_2023_android.ui.screen

import androidx.lifecycle.ViewModel
import com.dignicate.zero_2023_android.domain.MainUseCase
import com.dignicate.zero_2023_android.ui.ComposeNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainNavHostViewModel @Inject constructor(
    private val useCase: MainUseCase,
    val navigator: ComposeNavigator,
) : ViewModel() {

    fun onResume() {
        navigator.navigate(MainScreen.BookList)
    }
}
