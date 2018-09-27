package com.qingmei2.sample.ui.main.home.data

import com.qingmei2.rhine.base.repository.IRemoteDataSource
import com.qingmei2.sample.http.entity.ReceivedEvent
import io.reactivex.Flowable

interface IRemoteHomeDataSource : IRemoteDataSource {

    fun queryReceivedEvents(username: String): Flowable<List<ReceivedEvent>>
}