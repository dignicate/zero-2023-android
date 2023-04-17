package com.dignicate.zero_2023_android.ui.module

import com.dignicate.zero_2023_android.ui.ComposeNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
object NavigationModule {
    @Provides
    fun provideComposeNavigator() = ComposeNavigator()
}