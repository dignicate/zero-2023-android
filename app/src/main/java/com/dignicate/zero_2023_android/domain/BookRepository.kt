package com.dignicate.zero_2023_android.domain

import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun fetchBookList(): Flow<Result<BookList>>
    fun fetchBookDetail(id: Book.Id): Flow<Result<Book>>
}
