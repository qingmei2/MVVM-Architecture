package com.qingmei2.sample.ui.main.home.data

import com.qingmei2.rhine.base.repository.RhineRepositoryRemote
import com.qingmei2.sample.http.RxSchedulers
import com.qingmei2.sample.http.entity.ReceivedEvent
import com.qingmei2.sample.http.globalErrorTransformer
import com.qingmei2.sample.http.service.ServiceManager
import io.reactivex.Flowable

class HomeRepository(
        remoteDataSource: IRemoteHomeDataSource
) : RhineRepositoryRemote<IRemoteHomeDataSource>(remoteDataSource) {

    fun queryReceivedEvents(username: String): Flowable<List<ReceivedEvent>> =
            remoteDataSource.queryReceivedEvents(username)

}

class HomeRemoteDataSource(private val serviceManager: ServiceManager) : IRemoteHomeDataSource {

    override fun queryReceivedEvents(username: String): Flowable<List<ReceivedEvent>> =
            serviceManager.userService
                    .queryReceivedEvents(username)
                    .compose(globalErrorTransformer())
                    .subscribeOn(RxSchedulers.io)
}