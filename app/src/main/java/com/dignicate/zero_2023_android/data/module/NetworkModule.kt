package com.dignicate.zero_2023_android.data.module

import android.content.Context
import com.dignicate.zero_2023_android.data.service.api.ApiService
import com.dignicate.zero_2023_android.data.service.api.MockInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

/**
 * https://androidgeek.co/how-to-use-hilt-with-retrofit-complete-guide-part-3-d6fe55b6460f
 */
@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
object NetworkModule {

    @Provides
    @Singleton
    fun provideBaseUrl() = "https://dignicate.com"

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named("mockInterceptor")
        mockInterceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(mockInterceptor)
            .build()

    private var json: Json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: String,
        client: OkHttpClient
    ): ApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    @Named("mockInterceptor")
    fun provideMockInterceptor(context: Context): Interceptor = MockInterceptor(context)
}