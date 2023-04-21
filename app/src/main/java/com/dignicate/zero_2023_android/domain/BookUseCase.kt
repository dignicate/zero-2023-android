package com.dignicate.zero_2023_android.domain

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

interface BookUseCase {
    val bookInfo: Flow<BookList>
    val book: Flow<Book>
    suspend fun fetchBookList()
    suspend fun fetchBookDetail(id: Book.Id)
}

class BookUseCaseImpl @Inject constructor(
    private val repository: BookRepository,
) : BookUseCase {

    private val fetchBookListTrigger = MutableSharedFlow<Unit>()

    private val fetchBookDetailTrigger = MutableSharedFlow<Book.Id>()

    @OptIn(ExperimentalCoroutinesApi::class)
    override val bookInfo: Flow<BookList>
        get() = fetchBookListTrigger
            .flatMapLatest {
                repository.fetchBookList()
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val book: Flow<Book>
        get() = fetchBookDetailTrigger
            .flatMapLatest {
                repository.fetchBookDetail(it)
            }

    override suspend fun fetchBookList() {
        fetchBookListTrigger.emit(Unit)
    }

    override suspend fun fetchBookDetail(id: Book.Id) {
        fetchBookDetailTrigger.emit(id)
    }
}
