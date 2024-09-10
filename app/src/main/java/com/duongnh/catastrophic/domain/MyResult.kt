package com.duongnh.catastrophic.domain

sealed class MyResult<out T : Any, out U : Any> {
    data class Success<T : Any>(val data: T) : MyResult<T, Nothing>()
    data class Error<U : Any>(val rawResponse: U) : MyResult<Nothing, U>()
}