package com.dignicate.zero_2023_android.ui

import androidx.lifecycle.ViewModel
import com.dignicate.zero_2023_android.domain.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: MainUseCase,
) : ViewModel() {
}