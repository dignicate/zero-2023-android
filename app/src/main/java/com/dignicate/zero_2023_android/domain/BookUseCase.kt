package com.dignicate.zero_2023_android.domain

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import timber.log.Timber
import javax.inject.Inject

interface BookUseCase {
    val bookInfo: Flow<BookInfo>
    suspend fun fetchBookList()
}

class BookUseCaseImpl @Inject constructor(
    private val repository: BookRepository,
) : BookUseCase {

    private val fetchBookListTrigger = MutableSharedFlow<Unit>()

    @OptIn(ExperimentalCoroutinesApi::class)
    override val bookInfo: Flow<BookInfo>
        get() = fetchBookListTrigger
            .flatMapLatest {
                repository.fetchBookList()
            }

    override suspend fun fetchBookList() {
        Timber.d("fetchBookList()")
        fetchBookListTrigger.emit(Unit)
    }
}
