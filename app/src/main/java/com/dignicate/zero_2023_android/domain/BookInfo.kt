package com.dignicate.zero_2023_android.domain

data class BookInfo(
    val books: List<Book>,
) {
    data class Book(
        val title: String,
        val author: String,
    )
}
