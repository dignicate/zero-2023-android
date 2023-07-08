package com.dignicate.zero_2023_android.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dignicate.zero_2023_android.domain.Book
import com.dignicate.zero_2023_android.domain.BookUseCase
import com.dignicate.zero_2023_android.domain.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val useCase: BookUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState.create())

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
            .collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _uiState.value =
                            _uiState.value.copy(
                                book = resource.value.toViewData(),
                                showsLoadingIndicator = false,
                            )
                    }
                    is Resource.InProgress -> {
                        _uiState.value = _uiState.value.copy(showsLoadingIndicator = true)
                    }
                    is Resource.Error -> {
                        Timber.e(resource.throwable)
                    }
                }
            }
    }


    data class UiState(
        val book: Book?,
        val showsLoadingIndicator: Boolean,
    ) {
        data class Book(
            val title: String,
            val author: String,
            val publishedAt: String,
            val chapters: List<String>,
        )

        companion object {
            fun create(): UiState {
                return UiState(
                    book = null,
                    showsLoadingIndicator = false,
                )
            }
        }
    }
}

private fun Book.toViewData(): BookDetailViewModel.UiState.Book =
    BookDetailViewModel.UiState.Book(
        title = title,
        author = author,
        publishedAt = publishedAt,
        chapters = chapters,
    )

