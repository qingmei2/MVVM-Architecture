package com.qingmei2.sample.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
abstract class LoginDao {

    @Query("SELECT * FROM login_user WHERE username = :username")
    abstract fun findUserByName(username: String): Flowable<LoginEntity>

    @Insert
    abstract fun insert(user: LoginEntity): Long
}