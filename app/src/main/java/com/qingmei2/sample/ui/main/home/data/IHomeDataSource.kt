package com.qingmei2.sample.ui.main.home.data

import com.qingmei2.rhine.base.repository.IRemoteDataSource
import com.qingmei2.sample.http.entity.QueryUser
import io.reactivex.Flowable

interface IRemoteHomeDataSource : IRemoteDataSource {

    fun fetchUserInfo(username: String): Flowable<QueryUser>
}