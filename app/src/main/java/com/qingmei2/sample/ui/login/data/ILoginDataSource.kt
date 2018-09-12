package com.qingmei2.sample.ui.login.data

import com.qingmei2.rhine.base.repository.ILocalDataSource
import com.qingmei2.rhine.base.repository.IRemoteDataSource
import com.qingmei2.sample.db.LoginEntity
import com.qingmei2.sample.http.entity.LoginUser
import io.reactivex.Completable
import io.reactivex.Flowable

interface ILoginRemoteDataSource : IRemoteDataSource {

    fun login(username: String, password: String): Flowable<LoginUser>
}

interface ILoginLocalDataSource : ILocalDataSource {

    fun savePrefsUser(username: String,
                      password: String): Completable

    fun fetchPrefsUser(): Flowable<LoginEntity>
}