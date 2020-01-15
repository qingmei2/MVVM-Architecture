package com.qingmei2.architecture.core.util

/**
 * Used to allow Singleton with arguments in Kotlin while keeping the code efficient and safe.
 *
 * See https://medium.com/@BladeCoder/kotlin-singletons-with-argument-194ef06edd9e
 */
open class SingletonHolderSingleArg<out T, in A>(private val creator: (A) -> T) {

    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A): T =
            instance ?: synchronized(this) {
                instance ?: creator(arg).apply {
                    instance = this
                }
            }
}