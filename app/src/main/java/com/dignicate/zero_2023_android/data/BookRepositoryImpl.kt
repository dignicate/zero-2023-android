package com.dignicate.zero_2023_android.data

import com.dignicate.zero_2023_android.data.service.api.ApiService
import com.dignicate.zero_2023_android.data.service.api.dto.BookDetailDto
import com.dignicate.zero_2023_android.data.service.api.dto.BookListDto
import com.dignicate.zero_2023_android.domain.Book
import com.dignicate.zero_2023_android.domain.BookList
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

    override fun fetchBookList(): Flow<Result<BookList>> {
        return callbackFlow {
            val api = apiService.getBookList()
            api.enqueue(object: Callback<BookListDto> {
                override fun onResponse(call: Call<BookListDto>, response: Response<BookListDto>) {
                    response.body()?.toDomain()?.let { domain ->
                        trySend(Result.success(domain))
                    } ?: run {
                        trySend(Result.failure(RuntimeException()))
                    }
                }
                override fun onFailure(call: Call<BookListDto>, t: Throwable) {
                    trySend(Result.failure(t))
                }
            })
            awaitClose {
                api.cancel()
            }
        }
    }

    override fun fetchBookDetail(id: Book.Id): Flow<Result<Book>> {
        return callbackFlow {
            val api = apiService.getBookDetail(id.value)
            api.enqueue(object: Callback<BookDetailDto> {
                override fun onResponse(call: Call<BookDetailDto>, response: Response<BookDetailDto>) {
                    response.body()?.toDomain()?.let { domain ->
                        trySend(Result.success(domain))
                    } ?: run {
                        val code = response.code()
                        Timber.e("Unexpected response data. code: $code")
                        trySend(Result.failure(RuntimeException("パースに失敗しました")))
                    }
                }
                override fun onFailure(call: Call<BookDetailDto>, t: Throwable) {
                    trySend(Result.failure(t))
                }
            })
            awaitClose {
                api.cancel()
            }
        }
    }
}

private fun BookListDto.toDomain(): BookList {
    return BookList(
        books = books.map {
            BookList.BookSummary(
                id = Book.Id(it.id),
                title = it.title,
                author = it.author,
            )
        }
    )
}

private fun BookDetailDto.toDomain(): Book {
    return Book(
        id = Book.Id(id),
        title = title,
        author = author,
        publishedAt = publishedAt,
        chapters = chapters,
    )
}
