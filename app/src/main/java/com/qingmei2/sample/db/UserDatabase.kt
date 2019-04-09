package com.qingmei2.sample.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.qingmei2.sample.entity.ReceivedEvent

@Database(
        entities = [ReceivedEvent::class],
        version = 1
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userReceivedEventDao(): UserReceivedEventDao
}