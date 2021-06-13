package com.qingmei2.sample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import javax.inject.Singleton

@Module
@TestInstallIn(
        components = [SingletonComponent::class],
        replaces = [DispatcherModule::class]
)
class TestDispatcherModule {

    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = TestCoroutineDispatcher()
}
