package com.dignicate.zero_2023_android.domain

import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun fetchBookList(): Flow<BookList>
    fun fetchBookDetail(): Flow<Book>
}