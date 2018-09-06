package com.qingmei2.sample.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "login_user")
data class LoginEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Int,
        @ColumnInfo(name = "username")
        var username: String,
        @ColumnInfo(name = "password")
        var password: String,
        @ColumnInfo(name = "avatar_url")
        var avatarUrl: String
)