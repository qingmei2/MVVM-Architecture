package com.qingmei2.sample.ui.main.repos.data

import com.qingmei2.rhine.base.repository.ILocalDataSource
import com.qingmei2.rhine.base.repository.IRemoteDataSource
import com.qingmei2.rhine.base.repository.RhineRepositoryBoth
import com.qingmei2.sample.data.UserManager
import com.qingmei2.sample.http.RxSchedulers
import com.qingmei2.sample.http.entity.Repo
import com.qingmei2.sample.http.globalErrorTransformer
import com.qingmei2.sample.http.service.ServiceManager
import io.reactivex.Completable
import io.reactivex.Flowable
import org.intellij.lang.annotations.Flow

interface IRemoteReposDataSource : IRemoteDataSource {

    fun queryRepos(username: String): Flowable<List<Repo>>
}

interface ILocalReposDataSource : ILocalDataSource {

    fun saveReposToLocal(repos: List<Repo>): Completable
}

class ReposDataSource(remote: IRemoteReposDataSource,
                     local: ILocalReposDataSource) :
        RhineRepositoryBoth<IRemoteReposDataSource, ILocalReposDataSource>(remote, local) {

    fun queryRepos(username: String): Flowable<List<Repo>> =
            remoteDataSource.queryRepos(username)
                    .flatMap { repos ->
                        localDataSource.saveReposToLocal(repos)
                                .andThen(Flowable.just(repos))
                    }

}

class RemoteReposDataSource(private val serviceManager: ServiceManager) : IRemoteReposDataSource {

    override fun queryRepos(username: String): Flowable<List<Repo>> {
        return serviceManager.userService
                .queryRepos(username)
                .subscribeOn(RxSchedulers.io)
                .compose(globalErrorTransformer())
    }
}

class LocalReposDataSource() : ILocalReposDataSource {

    override fun saveReposToLocal(repos: List<Repo>): Completable {
        return Completable.complete()
    }
}