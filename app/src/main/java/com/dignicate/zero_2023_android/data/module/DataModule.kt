package com.dignicate.zero_2023_android.data.module

import com.dignicate.zero_2023_android.data.BookRepositoryImpl
import com.dignicate.zero_2023_android.data.MainRepositoryImpl
import com.dignicate.zero_2023_android.data.service.api.ApiService
import com.dignicate.zero_2023_android.domain.BookRepository
import com.dignicate.zero_2023_android.domain.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
object DataModule {
    @Provides
    fun provideMainRepository(): MainRepository = MainRepositoryImpl()
    @Provides
    fun provideBookRepository(apiService: ApiService): BookRepository = BookRepositoryImpl(apiService)
}
