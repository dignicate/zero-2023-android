package com.dignicate.zero_2023_android.data.service.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookListDto(
    @SerialName("books")
    val books: List<Book>,
) {
    @Serializable
    data class Book(
        @SerialName("id")
        val id: Long,
        @SerialName("title")
        val title: String,
        @SerialName("author")
        val author: String,
    )
}
