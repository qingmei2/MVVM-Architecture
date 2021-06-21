package com.qingmei2.sample.repository

import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.github.qingmei2.protobuf.UserPreferencesProtos
import com.github.qingmei2.protobuf.UserPreferencesSerializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * [UserInfoRepository] test.
 */
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@MediumTest
class UserInfoRepositoryTest {

    private var dataStore: DataStore<UserPreferencesProtos.UserPreferences> =
        InstrumentationRegistry.getInstrumentation().context.createDataStore(
            fileName = "user_prefs_test.pb",
            serializer = UserPreferencesSerializer
        )
    private var repository: UserInfoRepository = UserInfoRepository(dataStore)

    @Before
    fun initRepository() {

    }

    @After
    fun clearRepository() {
        GlobalScope.launch(Dispatchers.Main)  {
            dataStore.updateData { it ->
                it.toBuilder().clear().build()
            }
        }
    }

    @Test
    fun getDefaultInfoTest() {
        GlobalScope.launch(Dispatchers.Main)  {
            repository.fetchUserInfoFlow().collect { user: UserPreferencesProtos.UserPreferences ->
                assertEquals(user.username.isEmpty(), true)
                assertEquals(user.password.isEmpty(), true)
                assertEquals(user.autoLogin, false)
            }
        }
    }

    @Test
    fun saveUserInfoTest() {
        GlobalScope.launch(Dispatchers.Main) {
            val username = "qingmei2"
            val password = "123456"
            repository.saveUserInfo(username, password)
            repository.fetchUserInfoFlow().collect { user: UserPreferencesProtos.UserPreferences ->
                assertEquals(user.username, username)
                assertEquals(user.password, password)
                assertEquals(user.autoLogin, true)
            }
        }
    }
}
