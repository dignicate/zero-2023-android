package com.dignicate.zero_2023_android.ui.screen.different

import androidx.lifecycle.ViewModel
import com.dignicate.zero_2023_android.ui.ComposeNavigator
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
