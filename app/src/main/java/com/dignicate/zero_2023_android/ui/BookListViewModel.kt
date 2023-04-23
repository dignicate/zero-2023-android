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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val useCase: BookUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initialized)

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

    fun onBookClicked(id: Data.Item.Id) {

    }

    private suspend fun setupCoroutine() {
        useCase.bookInfo
            .collect {
                _uiState.emit(UiState.Success(it.toViewData()))
            }
    }

    data class Data(
        val items: List<Item>,
    ) {
        data class Item(
            val id: Id,
            val title: String,
            val author: String,
        ) {
            data class Id(val value: Long)
        }
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

        val isInProgress: Boolean
            get() = when (this) {
                is InProgress -> true
                is Success, is Failure, Initialized -> false
            }
    }
}

private fun BookList.toViewData(): BookListViewModel.Data =
    BookListViewModel.Data(
        items = books.map { it.toViewData() }
    )

private fun BookList.BookSummary.toViewData(): BookListViewModel.Data.Item =
    BookListViewModel.Data.Item(
        id = BookListViewModel.Data.Item.Id(id.value),
        title = title,
        author = author,
    )
