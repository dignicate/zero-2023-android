package com.dignicate.zero_2023_android.data.module

import com.dignicate.zero_2023_android.data.MainRepositoryImpl
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
    fun providesMainRepository(): MainRepository = MainRepositoryImpl()
}
