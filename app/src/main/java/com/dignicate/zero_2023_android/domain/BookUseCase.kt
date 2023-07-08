package com.dignicate.zero_2023_android.domain

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

interface BookUseCase {
    val bookInfo: Flow<Resource<BookList>>
    val bookDetail: Flow<Resource<Book>>
    suspend fun fetchBookList()
    suspend fun fetchBookDetail(id: Book.Id)
}

class BookUseCaseImpl @Inject constructor(
    private val repository: BookRepository,
) : BookUseCase {

    private val fetchBookListTrigger = MutableSharedFlow<Unit>()

    private val fetchBookDetailTrigger = MutableSharedFlow<Book.Id>()

    @OptIn(ExperimentalCoroutinesApi::class)
    override val bookInfo: Flow<Resource<BookList>> =
        fetchBookListTrigger
            .flatMapLatest {
                channelFlow {
                    send(Resource.InProgress)
                    try {
                        repository.fetchBookList()
                            .collect {
                                send(Resource.Success(it))
                            }
                    } catch (throwable: Throwable) {
                        send(Resource.Error(throwable))
                    }
                }
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val bookDetail: Flow<Resource<Book>> =
        fetchBookDetailTrigger
            .flatMapLatest {
                channelFlow {
                    send(Resource.InProgress)
                    try {
                        repository.fetchBookDetail(it)
                            .collect {
                                send(Resource.Success(it))
                            }
                    } catch (throwable: Throwable) {
                        send(Resource.Error(throwable))
                    }
                }
            }

    override suspend fun fetchBookList() {
        fetchBookListTrigger.emit(Unit)
    }

    override suspend fun fetchBookDetail(id: Book.Id) {
        fetchBookDetailTrigger.emit(id)
    }
}
