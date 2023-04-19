package com.dignicate.zero_2023_android.data.service.api

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import timber.log.Timber

/**
 * https://wahibhaq.medium.com/an-easy-way-to-mock-an-api-response-using-retrofit-okhttp-and-interceptor-7968e1f0d050
 */
class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val path = chain.request().url().toString()
        Timber.d("path: $path")
        return if (path.endsWith("dummy/book/list")) {
            proceedWithMockJson(chain, bookListMockResponse)
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

private const val bookListMockResponse = """
{ "books": [ { "title": "The War of the Worlds", "author": "H. G. Wells" }, { "title": "Dracula", "author": "Bram Stoker" }, { "title": "Oliver Twist", "author": "Charles Dickens" }, { "title": "Tess of the d'Urbervilles", "author": "Thomas Hardy" } ] }
"""