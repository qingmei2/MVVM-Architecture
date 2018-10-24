package com.qingmei2.sample.ui.main.home.data

import arrow.core.Either
import com.qingmei2.rhine.base.repository.IRemoteDataSource
import com.qingmei2.rhine.base.repository.RhineRepositoryRemote
import com.qingmei2.sample.entity.Errors
import com.qingmei2.sample.entity.ReceivedEvent
import com.qingmei2.sample.entity.Type
import com.qingmei2.sample.http.RxSchedulers
import com.qingmei2.sample.http.globalErrorTransformer
import com.qingmei2.sample.http.service.ServiceManager
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer

class HomeRepository(
        remoteDataSource: IRemoteHomeDataSource
) : RhineRepositoryRemote<IRemoteHomeDataSource>(remoteDataSource) {

    fun queryReceivedEvents(username: String): Flowable<Either<Errors, List<ReceivedEvent>>> =
            remoteDataSource.queryReceivedEvents(username)

}

class HomeRemoteDataSource(private val serviceManager: ServiceManager) : IRemoteHomeDataSource {

    override fun filterEvents(): FlowableTransformer<List<ReceivedEvent>, List<ReceivedEvent>> =
            FlowableTransformer { datas ->
                datas.flatMap { Flowable.fromIterable(it) }
                        .filter { it.type != Type.MemberEvent && it.type != Type.PublicEvent }
                        .toList()
                        .toFlowable()
            }

    override fun queryReceivedEvents(username: String): Flowable<Either<Errors, List<ReceivedEvent>>> =
            serviceManager.userService
                    .queryReceivedEvents(username)
                    .compose(filterEvents())        // except the MemberEvent
                    .compose(globalErrorTransformer())
                    .subscribeOn(RxSchedulers.io)
                    .map { list ->
                        when (list.isEmpty()) {
                            true -> Either.left(Errors.EmptyResultsError)
                            false -> Either.right(list)
                        }
                    }
}

interface IRemoteHomeDataSource : IRemoteDataSource {

    fun queryReceivedEvents(username: String): Flowable<Either<Errors, List<ReceivedEvent>>>

    fun filterEvents(): FlowableTransformer<List<ReceivedEvent>, List<ReceivedEvent>>
}