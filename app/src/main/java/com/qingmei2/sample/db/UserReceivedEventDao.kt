package com.qingmei2.sample.db

import androidx.annotation.WorkerThread
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.qingmei2.sample.entity.ReceivedEvent

@Dao
interface UserReceivedEventDao {

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(receivedEvents: List<ReceivedEvent>)

    @WorkerThread
    @Query("SELECT * FROM user_received_events ORDER BY indexInResponse ASC")
    fun queryEvents(): DataSource.Factory<Int, ReceivedEvent>

    @WorkerThread
    @Query("DELETE FROM user_received_events")
    suspend fun clearReceivedEvents()

    @WorkerThread
    @Query("SELECT MAX(indexInResponse) + 1 FROM user_received_events")
    suspend fun getNextIndexInReceivedEvents(): Int?
}