package com.qingmei2.sample.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable

/**
 * Not used.
 */
@Dao
abstract class LoginDao {

    @Query("SELECT * FROM login_user WHERE username = :username")
    abstract fun findUserByName(username: String): Flowable<LoginEntity>

    @Insert
    abstract fun insert(user: LoginEntity): Long
}