package com.dignicate.zero_2023_android.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dignicate.zero_2023_android.domain.Book
import com.dignicate.zero_2023_android.domain.BookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val useCase: BookUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initialized)

    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun onCreate() {
        viewModelScope.launch {
            setupCoroutine()
        }
    }

    fun onResume(id: Book.Id) {
        viewModelScope.launch {
            useCase.fetchBookDetail(id)
        }
    }

    private suspend fun setupCoroutine() {
        useCase.bookDetail
            .collect {
                _uiState.emit(UiState.Success(it.toViewData()))
            }
    }

    data class Data(
        val title: String,
        val author: String,
        val publishedAt: String,
        val chapters: List<String>,
    )

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

private fun Book.toViewData(): BookDetailViewModel.Data =
    BookDetailViewModel.Data(
        title = title,
        author = author,
        publishedAt = publishedAt,
        chapters = chapters,
    )

