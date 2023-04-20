package com.dignicate.zero_2023_android.data.service.api

import android.content.Context
import com.dignicate.zero_2023_android.R
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * https://wahibhaq.medium.com/an-easy-way-to-mock-an-api-response-using-retrofit-okhttp-and-interceptor-7968e1f0d050
 */
class MockInterceptor @Inject constructor(
    private val context: Context,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val path = chain.request().url().toString()
        Timber.d("path: $path")
        return if (path.endsWith("dummy/book/list")) {
            val response = context.resources.openRawResource(R.raw.book_list).bufferedReader().use { it.readText() }
            proceedWithMockJson(chain, response)
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
                ResponseBody.create(
                    MediaType.parse("application/json"),
                    mockJson.toByteArray()
                )
            )
            .addHeader("content-type", "application/json")
            .build()
    }
}
