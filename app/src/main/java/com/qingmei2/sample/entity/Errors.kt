package com.qingmei2.sample.entity

sealed class Errors : Throwable() {
    object NetworkError : Errors()
    object EmptyInputError : Errors()
    object EmptyResultsError : Errors()
}