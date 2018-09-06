package com.qingmei2.sample.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(
        entities = [LoginEntity::class],
        version = 1
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun loginDao(): LoginDao
}