package com.dignicate.zero_2023_android.domain

data class BookList(
    val books: List<BookSummary>,
) {
    data class BookSummary(
        val id: Book.Id,
        val title: String,
        val author: String,
    )
}
