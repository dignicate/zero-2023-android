package com.dignicate.zero_2023_android.data.service.api

import com.dignicate.zero_2023_android.data.service.api.dto.BookListDto
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("dummy/book/list")
    fun getBookList(): Call<BookListDto>
}
