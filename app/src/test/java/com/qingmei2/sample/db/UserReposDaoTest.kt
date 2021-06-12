package com.qingmei2.sample.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.filters.SmallTest
import androidx.test.runner.AndroidJUnit4
import com.qingmei2.sample.MainCoroutineRule
import com.qingmei2.sample.entity.Repo
import com.qingmei2.sample.utils.REPO_LIST_SIZE10
import com.qingmei2.sample.utils.readLocalJson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith

/**
 * [UserReposDao] test.
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
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

    @Test
    fun getNextIndexInReposTest() = runBlockingTest {
        val repoList = readLocalJson<List<Repo>>(REPO_LIST_SIZE10)
        repoList.forEachIndexed { index, repo ->
            repo.indexInSortResponse = index
        }
        database.userReposDao().insert(repoList)

        val nextIndexInRepos = database.userReposDao().getNextIndexInRepos()

        assertEquals(nextIndexInRepos, repoList.size)
    }
}
