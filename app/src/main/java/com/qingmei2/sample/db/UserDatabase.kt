package com.qingmei2.sample.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
        entities = [LoginEntity::class],
        version = 1
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun loginDao(): LoginDao
}