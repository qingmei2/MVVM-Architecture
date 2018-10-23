package com.qingmei2.sample.entity

sealed class Errors {
    object NetworkError : Errors()
    object EmptyResultsError : Errors()
}