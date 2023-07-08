package com.dignicate.zero_2023_android.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dignicate.zero_2023_android.domain.BookList
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
class BookListViewModel @Inject constructor(
    private val useCase: BookUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState.create())

    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            setupCoroutine()
        }
    }

    fun onCreate() {
        viewModelScope.launch {
            useCase.fetchBookList()
        }
    }

    private suspend fun setupCoroutine() {
        useCase.bookInfo
            .collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val bookList = resource.value
                        _uiState.value =
                            _uiState.value
                                .copy(
                                    books = bookList.books.map {
                                        it.toViewData()
                                    },
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
