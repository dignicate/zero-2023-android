package com.dignicate.zero_2023_android.domain.module

import com.dignicate.zero_2023_android.domain.BookUseCase
import com.dignicate.zero_2023_android.domain.BookUseCaseImpl
import com.dignicate.zero_2023_android.domain.MainUseCase
import com.dignicate.zero_2023_android.domain.MainUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
abstract class DomainModule {
    @Binds
    abstract fun bindMainUseCase(mainUseCase: MainUseCaseImpl): MainUseCase
    @Binds
    abstract fun bindBookUseCase(bookUseCase: BookUseCaseImpl): BookUseCase
}
