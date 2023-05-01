package com.dignicate.zero_2023_android.ui

import android.os.Bundle

class Argument<T>(val key: String) {

    fun pairWith(value: T?) = key to value

    @Suppress("UNCHECKED_CAST", "DEPRECATION")
    fun typedValue(bundle: Bundle?) = bundle?.get(key) as? T
}
