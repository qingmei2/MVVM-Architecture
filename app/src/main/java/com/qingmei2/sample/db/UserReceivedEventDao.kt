package com.qingmei2.sample.db

import androidx.annotation.VisibleForTesting
import androidx.annotation.WorkerThread
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.qingmei2.sample.entity.ReceivedEvent
import com.qingmei2.sample.entity.Repo

@Dao
interface UserReceivedEventDao {

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(receivedEvents: List<ReceivedEvent>)

    @WorkerThread
    @Query("SELECT * FROM user_received_events ORDER BY indexInResponse ASC")
    fun queryEvents(): PagingSource<Int, ReceivedEvent>

    @WorkerThread
    @Query("DELETE FROM user_received_events")
    suspend fun clearReceivedEvents()

    @WorkerThread
    @Query("SELECT MAX(indexInResponse) + 1 FROM user_received_events")
    suspend fun getNextIndexInReceivedEvents(): Int?

    /**
     * 根据某个Id检索对应的 [ReceivedEvent].
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    @Query("SELECT * FROM user_received_events WHERE id = :eventId")
    suspend fun getEventById(eventId: String): ReceivedEvent?

    /**
     * 获取所有的 [ReceivedEvent].
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    @Query("SELECT * FROM user_received_events")
    suspend fun getAllReceivedEvents(): List<ReceivedEvent>
}
