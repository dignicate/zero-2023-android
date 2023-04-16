package com.dignicate.zero_2023_android.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initialized)

    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    data class Data(
        val items: List<Item>,
    ) {
        data class Item(
            val title: String,
            val author: String,
        )
    }

    sealed interface UiState {
        data class Success(val value: Data) : UiState
        data class InProgress(val value: Data?) : UiState
        data class Failure(val throwable: Throwable) : UiState
        object Initialized : UiState

        val data: Data?
            get() = when (this) {
                is Success -> value
                is InProgress -> value
                is Failure, Initialized -> null
            }
    }
}