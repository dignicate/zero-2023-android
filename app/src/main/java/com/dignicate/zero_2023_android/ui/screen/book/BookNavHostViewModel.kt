package com.dignicate.zero_2023_android.ui.screen.book

import androidx.lifecycle.ViewModel
import com.dignicate.zero_2023_android.ui.ComposeNavigator
import com.dignicate.zero_2023_android.ui.screen.different.DifferentScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookNavHostViewModel @Inject constructor(
    val navigator: ComposeNavigator,
) : ViewModel() {

    fun onCreate() {
//        navigator.navigate(BookScreen.BookList)
        navigator.navigate(DifferentScreen.Top)
    }
}
