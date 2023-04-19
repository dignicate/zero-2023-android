package com.dignicate.zero_2023_android.data

import com.dignicate.zero_2023_android.data.service.api.ApiService
import com.dignicate.zero_2023_android.data.service.api.dto.BookListDto
import com.dignicate.zero_2023_android.domain.BookInfo
import com.dignicate.zero_2023_android.domain.BookRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : BookRepository {

    override fun fetchBookList(): Flow<BookInfo> {
        Timber.d("fetchBookList()")
        return callbackFlow {
            val api = apiService.getBookList()
            api.enqueue(object: Callback<BookListDto> {
                override fun onResponse(call: Call<BookListDto>, response: Response<BookListDto>) {
                    response.body()?.let {
                        val domain = it.toDomain()
                        trySendBlocking(domain)
                            .onFailure { e -> Timber.e(e) }
                    } ?: run {
                        val code = response.code()
                        Timber.e("Unexpected response data. code: $code")
                    }
                }
                override fun onFailure(call: Call<BookListDto>, t: Throwable) {
                    Timber.e(t)
                }
            })
            awaitClose {
                api.cancel()
            }
        }
    }
}

private fun BookListDto.toDomain(): BookInfo {
    return BookInfo(
        books = books.map {
            BookInfo.Book(
                title = it.title,
                author = it.author,
            )
        }
    )
}
