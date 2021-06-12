package com.qingmei2.sample.db

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.qingmei2.sample.MainCoroutineRule
import com.qingmei2.sample.entity.Repo
import com.qingmei2.sample.utils.REPO_LIST_SIZE10
import com.qingmei2.sample.utils.readLocalJson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class UserReposDaoTest {

    private lateinit var database: UserDatabase

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

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
    fun insertRepoListTest() = runBlockingTest {
        val repoList = readLocalJson<List<Repo>>(REPO_LIST_SIZE10)
        database.userReposDao().insert(repoList)

        val insertedList = database.userReposDao().getAllRepos()

        assertEquals(repoList.size, insertedList.size)

        val randomItem: Repo = repoList[3]
        val insertedItem = database.userReposDao().getRepoById(randomItem.id.toString())
        assertEquals(insertedItem.hashCode(), randomItem.hashCode())
    }

    @Test
    fun deleteAllReposTest() = runBlockingTest {
        val repoList = readLocalJson<List<Repo>>(REPO_LIST_SIZE10)
        database.userReposDao().insert(repoList)

        database.userReposDao().deleteAllRepos()

        val allRepos = database.userReposDao().getAllRepos()

        assertEquals(allRepos.size, 0)
    }
}
