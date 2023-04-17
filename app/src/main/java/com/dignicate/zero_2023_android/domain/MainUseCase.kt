package com.dignicate.zero_2023_android.domain

import javax.inject.Inject

interface MainUseCase {
}

class MainUseCaseImpl @Inject constructor(
    repository: MainRepository,
) : MainUseCase {

}
