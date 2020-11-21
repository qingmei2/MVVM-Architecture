package com.qingmei2.sample.repository

import androidx.datastore.DataStore
import com.github.qingmei2.protobuf.UserPreferencesProtos
import com.qingmei2.architecture.core.util.SingletonHolderSingleArg
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

@Suppress("PrivatePropertyName")
class UserInfoRepository(private val dataStore: DataStore<UserPreferencesProtos.UserPreferences>) {

    fun fetchUserInfoFlow(): Flow<UserPreferencesProtos.UserPreferences> {
        return dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(UserPreferencesProtos.UserPreferences.getDefaultInstance())
            } else {
                throw it
            }
        }
    }

    suspend fun saveUserInfo(username: String,
                             password: String) {
        dataStore.updateData { userPreferences ->
            userPreferences.toBuilder()
                    .setUsername(username)
                    .setPassword(password)
                    .setAutoLogin(username.isNotEmpty() && password.isNotEmpty())
                    .build()
        }
    }

    companion object :
            SingletonHolderSingleArg<UserInfoRepository, DataStore<UserPreferencesProtos.UserPreferences>>(::UserInfoRepository)
}