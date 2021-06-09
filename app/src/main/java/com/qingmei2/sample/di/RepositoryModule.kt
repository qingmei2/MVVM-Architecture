package com.qingmei2.sample.di

import android.app.Application
import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import androidx.datastore.preferences.Preferences
import com.github.qingmei2.protobuf.UserPreferencesProtos
import com.github.qingmei2.protobuf.UserPreferencesSerializer
import com.qingmei2.sample.repository.UserInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserInfoRepository(userDataStore: DataStore<UserPreferencesProtos.UserPreferences>): UserInfoRepository {
        return UserInfoRepository.getInstance(userDataStore)
    }

    @Provides
    @Singleton
    fun providePreferencesDataStore(application: Application): DataStore<UserPreferencesProtos.UserPreferences> {
        return application.createDataStore(
                fileName = "user_prefs.pb",
                serializer = UserPreferencesSerializer
        )
    }
}
