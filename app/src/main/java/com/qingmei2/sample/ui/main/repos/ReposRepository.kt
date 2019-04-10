package com.qingmei2.sample.ui.main.repos

import arrow.core.Either
import com.qingmei2.rhine.base.repository.BaseRepositoryBoth
import com.qingmei2.rhine.base.repository.ILocalDataSource
import com.qingmei2.rhine.base.repository.IRemoteDataSource
import com.qingmei2.rhine.util.RxSchedulers
import com.qingmei2.sample.entity.Errors
import com.qingmei2.sample.entity.Repo
import com.qingmei2.sample.http.globalHandleError
import com.qingmei2.sample.http.service.ServiceManager
import io.reactivex.Completable
import io.reactivex.Flowable

class ReposRepository(
        remote: RemoteReposDataSource,
        local: LocalReposDataSource
) : BaseRepositoryBoth<RemoteReposDataSource, LocalReposDataSource>(remote, local) {

    fun queryRepos(
            username: String,
            pageIndex: Int,
            perPage: Int,
            sort: String
    ): Flowable<Either<Errors, List<Repo>>> =
            remoteDataSource.queryRepos(username, pageIndex, perPage, sort)
                    .flatMap { reposEither ->
                        localDataSource.saveReposToLocal(reposEither)
                                .andThen(Flowable.just(reposEither))
                    }
}

class RemoteReposDataSource(private val serviceManager: ServiceManager) : IRemoteDataSource {

    fun queryRepos(username: String,
                   pageIndex: Int,
                   perPage: Int,
                   sort: String): Flowable<Either<Errors, List<Repo>>> {
        return serviceManager.userService
                .queryRepos(username, pageIndex, perPage, sort)
                .subscribeOn(RxSchedulers.io)
                .map {
                    when (it.isEmpty()) {
                        true -> Either.left(Errors.EmptyResultsError)
                        false -> Either.right(it)
                    }
                }
                .compose(globalHandleError())
    }
}

class LocalReposDataSource : ILocalDataSource {

    fun saveReposToLocal(repos: Either<Errors, List<Repo>>): Completable {
        return Completable.complete()
    }
}