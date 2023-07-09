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
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val useCase: BookUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState.create())

    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        setupBinding()
    }

    fun onCreate(id: Book.Id) {
        viewModelScope.launch {
            useCase.fetchBookDetail(id)
        }
    }

    private fun setupBinding() {
        viewModelScope.launch {
            useCase.bookDetail
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            _uiState.value =
                                _uiState.value.success(
                                    book = resource.value.toViewData(),
                                )
                        }

                        is Resource.InProgress -> {
                            _uiState.value = _uiState.value.inProgress()
                        }

                        is Resource.Error -> {
                            _uiState.value = _uiState.value.error(resource.throwable)
                        }
                    }
                }
        }
    }

    data class UiState(
        val book: Book?,
        val showsLoadingIndicator: Boolean,
        val errorMessage: String?,
    ) {

        fun success(book: Book): UiState {
            return copy(
                book = book,
                showsLoadingIndicator = false,
                errorMessage = null,
            )
        }

        fun inProgress(): UiState {
            return copy(
                showsLoadingIndicator = true,
                errorMessage = null,
            )
        }

        fun error(throwable: Throwable): UiState {
            return copy(
                showsLoadingIndicator = false,
                errorMessage = throwable.message,
            )
        }

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
                    errorMessage = null,
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

