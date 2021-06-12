package com.qingmei2.sample.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.qingmei2.sample.MainCoroutineRule
import com.qingmei2.sample.entity.ReceivedEvent
import com.qingmei2.sample.utils.EVENT_LIST_SIZE12
import com.qingmei2.sample.utils.readLocalJson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith

/**
 * [UserReceivedEventDao] test.
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class UserReceivedEventDaoTest {

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
                ApplicationProvider.getApplicationContext(),
                UserDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertListTest() = runBlockingTest {
        val events = readLocalJson<List<ReceivedEvent>>(EVENT_LIST_SIZE12)
        database.userReceivedEventDao().insert(events)

        val insertedList = database.userReceivedEventDao().getAllReceivedEvents()

        Assert.assertEquals(events.size, insertedList.size)

        val randomItem: ReceivedEvent = events[3]
        val insertedItem = database.userReceivedEventDao().getEventById(randomItem.id.toString())
        Assert.assertEquals(insertedItem.hashCode(), randomItem.hashCode())
    }

    @Test
    fun clearReceivedEventsTest() = runBlockingTest {
        val events = readLocalJson<List<ReceivedEvent>>(EVENT_LIST_SIZE12)
        database.userReceivedEventDao().insert(events)

        database.userReceivedEventDao().clearReceivedEvents()

        val allItems = database.userReceivedEventDao().getAllReceivedEvents()

        Assert.assertEquals(allItems.size, 0)
    }

    @Test
    fun getNextIndexInReceivedEventsTest() = runBlockingTest {
        val events = readLocalJson<List<ReceivedEvent>>(EVENT_LIST_SIZE12)
        events.forEachIndexed { index, event ->
            event.indexInResponse = index
        }
        database.userReceivedEventDao().insert(events)

        val nextEvent = database.userReceivedEventDao().getNextIndexInReceivedEvents()

        Assert.assertEquals(nextEvent, events.size)
    }
}
