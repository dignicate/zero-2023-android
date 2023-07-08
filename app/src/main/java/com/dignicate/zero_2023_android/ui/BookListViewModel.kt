package com.dignicate.zero_2023_android.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dignicate.zero_2023_android.domain.BookList
import com.dignicate.zero_2023_android.domain.BookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val useCase: BookUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.create())

    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun onCreate() {
        viewModelScope.launch {
            setupCoroutine()
        }
    }

    fun onResume() {
        viewModelScope.launch {
            useCase.fetchBookList()
        }
    }

    fun onBookClicked(id: UiState.BookSummary.Id) {
        viewModelScope.launch {
            useCase.fetchBookList()
        }
    }

    private suspend fun setupCoroutine() {
        useCase.bookInfo
            .collect { bookList ->
                _uiState.value =
                    _uiState.value
                        .copy(
                            books = bookList.books.map {
                                it.toViewData()
                            }
                        )
            }
    }

    data class UiState(
        val books: List<BookSummary>,
        val showsLoadingIndicator: Boolean,
    ) {
        data class BookSummary(
            val id: Id,
            val title: String,
            val author: String,
        ) {
            data class Id(val value: Long)
        }

        companion object {
            fun create(): UiState {
                return UiState(
                    books = emptyList(),
                    showsLoadingIndicator = false,
                )
            }
        }
    }
}

private fun BookList.BookSummary.toViewData(): BookListViewModel.UiState.BookSummary =
    BookListViewModel.UiState.BookSummary(
        id = BookListViewModel.UiState.BookSummary.Id(id.value),
        title = title,
        author = author,
    )
