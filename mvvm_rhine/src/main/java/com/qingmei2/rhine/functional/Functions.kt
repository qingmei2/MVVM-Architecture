package com.qingmei2.rhine.functional

typealias Supplier<T> = () -> T

interface Consumer<T> {

    fun accept(t: T)
}