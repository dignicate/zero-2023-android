package com.dignicate.zero_2023_android.domain

data class Book(
    val id: Id,
    val title: String,
    val author: String,
) {
    data class Id(val value: Long)
}