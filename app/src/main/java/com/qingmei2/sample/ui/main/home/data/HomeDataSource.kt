package com.qingmei2.sample.ui.main.home.data

import com.qingmei2.rhine.base.repository.RhineRepositoryRemote
import com.qingmei2.sample.http.RxSchedulers
import com.qingmei2.sample.http.entity.QueryUser
import com.qingmei2.sample.http.service.ServiceManager
import io.reactivex.Flowable
import org.intellij.lang.annotations.Flow

class HomeRepository(
        remoteDataSource: IRemoteHomeDataSource
) : RhineRepositoryRemote<IRemoteHomeDataSource>(remoteDataSource) {

    fun fetchUserInfo(username: String): Flowable<QueryUser> =
            remoteDataSource.fetchUserInfo(username)
}

class HomeRemoteDataSource(val serviceManager: ServiceManager) : IRemoteHomeDataSource {

    override fun fetchUserInfo(username: String): Flowable<QueryUser> =
            serviceManager.userService
                    .queryUser(username)
                    .subscribeOn(RxSchedulers.io)
}