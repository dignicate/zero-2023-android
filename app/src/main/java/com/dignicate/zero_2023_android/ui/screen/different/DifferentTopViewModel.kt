package com.dignicate.zero_2023_android.ui.screen.different

import androidx.lifecycle.ViewModel
import com.dignicate.zero_2023_android.ui.ComposeNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DifferentTopViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState.create())

    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun onCreate() {
        Timber.d("onCreate()")
    }

    fun onMenuClicked() {
        Timber.d("onMenuClicked()")
    }

    data class UiState(
        val value: String?,
    ) {
        companion object {
            fun create(): UiState {
                return UiState(null)
            }
        }
    }
}
