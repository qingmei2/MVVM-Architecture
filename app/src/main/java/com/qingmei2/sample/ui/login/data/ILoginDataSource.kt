package com.qingmei2.sample.ui.login.data

import com.qingmei2.rhine.base.repository.ILocalDataSource
import com.qingmei2.rhine.base.repository.IRemoteDataSource
import com.qingmei2.sample.db.LoginEntity
import com.qingmei2.sample.http.entity.LoginUser
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface ILoginRemoteDataSource : IRemoteDataSource {

    fun login(username: String, password: String): Flowable<LoginUser>
}

interface ILoginLocalDataSource : ILocalDataSource {

    fun insertUser(user: LoginUser): Completable

    fun findUserByUsername(username: String): Flowable<LoginEntity>
}