package com.qingmei2.sample.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.filters.SmallTest
import com.github.qingmei2.protobuf.UserPreferencesProtos
import com.github.qingmei2.protobuf.UserPreferencesSerializer
import com.qingmei2.architecture.core.base.BaseApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

/**
 * [UserInfoRepository] test.
 */
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@MediumTest
class UserInfoRepositoryTest {

    private lateinit var dataStore: DataStore<UserPreferencesProtos.UserPreferences>
    private lateinit var repository: UserInfoRepository

    @Before
    fun initRepository() {
        dataStore = BaseApplication.INSTANCE.createDataStore(
                fileName = "user_prefs_test.pb",
                serializer = UserPreferencesSerializer
        )

        repository = UserInfoRepository(dataStore)
    }

    @After
    fun clearRepository() = runBlocking {
        dataStore.updateData { it ->
            it.toBuilder().clear().build()
        }
    }

    @Test
    fun getDefaultInfoTest() {
        runBlocking {
            repository.fetchUserInfoFlow().collect { user: UserPreferencesProtos.UserPreferences ->
                Assert.assertEquals(user.username.isEmpty(), true)
                Assert.assertEquals(user.password.isEmpty(), true)
                Assert.assertEquals(user.autoLogin, false)
            }
        }
    }
}
