package com.dignicate.zero_2023_android.data.service.api.dto

import com.google.gson.annotations.SerializedName

data class BookListDto(
    @SerializedName("books")
    val books: List<Book>,
) {
    data class Book(
        @SerializedName("title")
        val title: String,
        @SerializedName("author")
        val author: String,
    )
}
