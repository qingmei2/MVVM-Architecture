package com.qingmei2.sample.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login_user")
data class LoginEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Int,
        @ColumnInfo(name = "username")
        var username: String,
        @ColumnInfo(name = "password")
        var password: String
)