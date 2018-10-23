package com.qingmei2.sample.ui.main.home.data

import com.qingmei2.rhine.base.repository.IRemoteDataSource
import com.qingmei2.sample.entity.ReceivedEvent
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer

interface IRemoteHomeDataSource : IRemoteDataSource {

    fun queryReceivedEvents(username: String): Flowable<List<ReceivedEvent>>

    fun filterEvents(): FlowableTransformer<List<ReceivedEvent>, List<ReceivedEvent>>
}