package com.dignicate.zero_2023_android.domain

sealed interface Resource<out T: Any?> {
    data class Success<T>(val value: T) : Resource<T>
    object InProgress : Resource<Nothing>
    data class Error(val throwable: Throwable) : Resource<Nothing>
}
