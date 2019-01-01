package com.qingmei2.sample.ui.main.home

import arrow.core.Either
import com.qingmei2.rhine.base.repository.BaseRepositoryRemote
import com.qingmei2.rhine.base.repository.IRemoteDataSource
import com.qingmei2.sample.entity.DISPLAY_EVENT_TYPES
import com.qingmei2.sample.entity.Errors
import com.qingmei2.sample.entity.ReceivedEvent
import com.qingmei2.rhine.util.RxSchedulers
import com.qingmei2.sample.http.globalHandleError
import com.qingmei2.sample.http.service.ServiceManager
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer

class HomeRepository(
        remoteDataSource: IRemoteHomeDataSource
) : BaseRepositoryRemote<IRemoteHomeDataSource>(remoteDataSource) {

    fun queryReceivedEvents(username: String,
                            pageIndex: Int,
                            perPage: Int): Flowable<Either<Errors, List<ReceivedEvent>>> =
            remoteDataSource.queryReceivedEvents(username, pageIndex, perPage)

}

class HomeRemoteDataSource(private val serviceManager: ServiceManager) : IRemoteHomeDataSource {

    override fun filterEvents(): FlowableTransformer<List<ReceivedEvent>, List<ReceivedEvent>> =
            FlowableTransformer { datas ->
                datas.flatMap { Flowable.fromIterable(it) }
                        .filter { DISPLAY_EVENT_TYPES.contains(it.type) }
                        .toList()
                        .toFlowable()
            }

    override fun queryReceivedEvents(username: String,
                                     pageIndex: Int,
                                     perPage: Int): Flowable<Either<Errors, List<ReceivedEvent>>> =
            serviceManager.userService
                    .queryReceivedEvents(username, pageIndex, perPage)
                    .compose(filterEvents())        // except the MemberEvent
                    .compose(globalHandleError())
                    .subscribeOn(RxSchedulers.io)
                    .map { list ->
                        when (list.isEmpty()) {
                            true -> Either.left(Errors.EmptyResultsError)
                            false -> Either.right(list)
                        }
                    }
}

interface IRemoteHomeDataSource : IRemoteDataSource {

    fun queryReceivedEvents(username: String,
                            pageIndex: Int,
                            perPage: Int): Flowable<Either<Errors, List<ReceivedEvent>>>

    fun filterEvents(): FlowableTransformer<List<ReceivedEvent>, List<ReceivedEvent>>
}