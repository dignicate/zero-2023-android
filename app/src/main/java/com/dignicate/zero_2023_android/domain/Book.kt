package com.dignicate.zero_2023_android.domain

data class Book(
    val id: Id,
    val title: String,
    val author: String,
    val publishedAt: String,
    val chapters: List<String>,
) {
    data class Id(val value: Long)
}