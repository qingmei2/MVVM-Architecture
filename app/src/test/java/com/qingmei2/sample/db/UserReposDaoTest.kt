package com.qingmei2.sample.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.qingmei2.sample.MainCoroutineRule
import com.qingmei2.sample.entity.Repo
import com.qingmei2.sample.utils.REPO_LIST_SIZE10
import com.qingmei2.sample.utils.readLocalJson
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class UserReposDaoTest {

    private lateinit var database: UserDatabase

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // 指定架构组件内部异步的Executor变为同步执行
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        // 创建内存数据库，保证进程结束时清空数据
        database = Room.inMemoryDatabaseBuilder(
                getApplicationContext(),
                UserDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertRepoListTest() {
        runBlockingTest {
            val repoList = readLocalJson<List<Repo>>(REPO_LIST_SIZE10)
            database.userReposDao().insert(repoList)

            val insertedList = database.userReposDao().getAllRepos()

            assertEquals(repoList.size, insertedList.size)

            val randomItem: Repo = repoList[3]
            val insertedItem = database.userReposDao().getRepoById(randomItem.id.toString())
            assertEquals(insertedItem.hashCode(), randomItem.hashCode())
        }
    }
}
