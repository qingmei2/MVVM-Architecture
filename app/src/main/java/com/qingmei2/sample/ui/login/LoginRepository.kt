package com.qingmei2.sample.ui.login

import arrow.core.Either
import com.qingmei2.rhine.base.repository.BaseRepositoryBoth
import com.qingmei2.rhine.base.repository.ILocalDataSource
import com.qingmei2.rhine.base.repository.IRemoteDataSource
import com.qingmei2.rhine.util.RxSchedulers
import com.qingmei2.sample.db.UserDatabase
import com.qingmei2.sample.entity.Errors
import com.qingmei2.sample.entity.LoginEntity
import com.qingmei2.sample.entity.LoginUser
import com.qingmei2.sample.http.service.ServiceManager
import com.qingmei2.sample.manager.PrefsHelper
import com.qingmei2.sample.manager.UserManager
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class LoginRepository(
        remoteDataSource: LoginRemoteDataSource,
        localDataSource: LoginLocalDataSource
) : BaseRepositoryBoth<LoginRemoteDataSource, LoginLocalDataSource>(remoteDataSource, localDataSource) {

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

    fun prefsUser(): Flowable<Either<Errors, LoginEntity>> =
            localDataSource.fetchPrefsUser()

    fun prefsAutoLogin(): Single<Boolean> =
            localDataSource.isAutoLogin()
}

class LoginRemoteDataSource(
        private val serviceManager: ServiceManager
) : IRemoteDataSource {

    fun login(username: String, password: String): Flowable<Either<Errors, LoginUser>> =
            serviceManager.loginService
                    .login(username, password)
                    .subscribeOn(RxSchedulers.io)
                    .map {
                        Either.right(it)
                    }
}

class LoginLocalDataSource(
        private val db: UserDatabase,
        private val prefs: PrefsHelper
) : ILocalDataSource {

    fun isAutoLogin(): Single<Boolean> =
            Single.just(prefs.autoLogin)

    fun savePrefsUser(username: String, password: String): Completable =
            Completable.fromAction {
                prefs.username = username
                prefs.password = password
            }

    fun fetchPrefsUser(): Flowable<Either<Errors, LoginEntity>> =
            Flowable.just(prefs)
                    .map {
                        when (it.username.isNotEmpty() && it.password.isNotEmpty()) {
                            true -> Either.right(LoginEntity(1, it.username, it.password))
                            false -> Either.left(Errors.EmptyResultsError)
                        }
                    }
}