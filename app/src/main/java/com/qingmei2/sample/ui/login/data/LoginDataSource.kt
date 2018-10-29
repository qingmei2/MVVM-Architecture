package com.qingmei2.sample.ui.login.data

import arrow.core.Either
import com.qingmei2.rhine.base.repository.RhineRepositoryBoth
import com.qingmei2.sample.manager.PrefsHelper
import com.qingmei2.sample.db.LoginEntity
import com.qingmei2.sample.db.UserDatabase
import com.qingmei2.sample.entity.Errors
import com.qingmei2.sample.entity.LoginUser
import com.qingmei2.sample.http.RxSchedulers
import com.qingmei2.sample.http.service.ServiceManager
import com.qingmei2.sample.manager.UserManager
import io.reactivex.Completable
import io.reactivex.Flowable

class LoginDataSourceRepository(
        remoteDataSource: ILoginRemoteDataSource,
        localDataSource: ILoginLocalDataSource
) : RhineRepositoryBoth<ILoginRemoteDataSource, ILoginLocalDataSource>(remoteDataSource, localDataSource) {

    fun login(username: String, password: String): Flowable<Either<Errors, LoginUser>> =
            remoteDataSource.login(username, password)
                    .doOnNext { either ->
                        either.fold({

                        }, {
                            UserManager.INSTANCE = it
                        })
                    }
                    .flatMap {
                        localDataSource.savePrefsUser(username, password)  // save user
                                .andThen(Flowable.just(it))
                    }

    fun prefsUser(): Flowable<Either<Errors, LoginEntity>> = localDataSource.fetchPrefsUser()
}

class LoginRemoteDataSource(
        private val serviceManager: ServiceManager
) : ILoginRemoteDataSource {

    override fun login(username: String, password: String): Flowable<Either<Errors, LoginUser>> =
            serviceManager.loginService
                    .login(username, password)
                    .subscribeOn(RxSchedulers.io)
                    .map {
                        Either.right(it)
                    }
}

class LoginLocalDataSource(
        private val database: UserDatabase,
        private val prefs: PrefsHelper
) : ILoginLocalDataSource {

    override fun savePrefsUser(username: String, password: String): Completable =
            Completable.fromAction {
                prefs.username = username
                prefs.password = password
            }

    override fun fetchPrefsUser(): Flowable<Either<Errors, LoginEntity>> =
            Flowable.just(prefs)
                    .map {
                        when (it.username == "" || it.password == "") {
                            true -> Either.left(Errors.EmptyResultsError)
                            false -> Either.right(LoginEntity(1, it.username, it.password))
                        }
                    }
}