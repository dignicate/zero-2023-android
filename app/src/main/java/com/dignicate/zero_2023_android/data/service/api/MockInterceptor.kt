package com.dignicate.zero_2023_android.data.service.api

import android.content.Context
import com.dignicate.zero_2023_android.R
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import timber.log.Timber
import javax.inject.Inject

/**
 * https://wahibhaq.medium.com/an-easy-way-to-mock-an-api-response-using-retrofit-okhttp-and-interceptor-7968e1f0d050
 */
class MockInterceptor @Inject constructor(
    private val context: Context,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val path = chain.request().url.toString()
        Timber.d("intercept() path: $path")
        return if (path.endsWith("dummy/book/list")) {
            val response = context.resources.openRawResource(R.raw.book_list).bufferedReader().use { it.readText() }
            proceedWithMockJson(chain, response)
        } else if (path.endsWith("dummy/book/detail/1")) {
            val response = context.resources.openRawResource(R.raw.book_detail_1).bufferedReader().use { it.readText() }
            proceedWithMockJson(chain, response)
        } else if (path.endsWith("dummy/book/detail/2")) {
            val response = context.resources.openRawResource(R.raw.book_detail_2).bufferedReader().use { it.readText() }
            proceedWithMockJson(chain, response)
        } else if (path.endsWith("dummy/book/detail")) {
            proceedWithMockJson(chain, "")
        } else {
            chain.proceed(chain.request())
        }
    }

    private fun proceedWithMockJson(chain: Interceptor.Chain, mockJson: String): Response {
        return chain.proceed(chain.request())
            .newBuilder()
            .code(200)
            .protocol(Protocol.HTTP_2)
            .message(mockJson)
            .body(
                mockJson.toByteArray()
                    .toResponseBody("application/json".toMediaType())
            )
            .addHeader("content-type", "application/json")
            .build()
    }
}
