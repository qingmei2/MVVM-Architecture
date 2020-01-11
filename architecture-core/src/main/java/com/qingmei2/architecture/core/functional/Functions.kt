package com.qingmei2.architecture.core.functional

typealias Supplier<T> = () -> T

interface Consumer<T> {

    fun accept(t: T)
}