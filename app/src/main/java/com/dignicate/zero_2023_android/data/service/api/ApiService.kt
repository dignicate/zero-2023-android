package com.dignicate.zero_2023_android.data.service.api

import com.dignicate.zero_2023_android.data.service.api.dto.BookDetailDto
import com.dignicate.zero_2023_android.data.service.api.dto.BookListDto
import com.dignicate.zero_2023_android.ui.ComposeScreen
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("dummy/book/list")
    fun getBookList(): Call<BookListDto>

    @GET("dummy/book/detail/{id}")
    fun getBookDetail(@Path("id") bookId: Long): Call<BookDetailDto>
}
