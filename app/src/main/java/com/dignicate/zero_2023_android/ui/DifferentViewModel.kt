package com.dignicate.zero_2023_android.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DifferentViewModel @Inject constructor(
    val navigator: ComposeNavigator,
) : ViewModel() {

    fun onResume() {
        navigator.navigate(DifferentScreen.Top)
    }
}
