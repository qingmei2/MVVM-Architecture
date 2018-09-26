package com.qingmei2.sample.ui.login.data

import com.qingmei2.rhine.base.repository.RhineRepositoryBoth
import com.qingmei2.sample.PrefsHelper
import com.qingmei2.sample.data.UserManager
import com.qingmei2.sample.db.LoginEntity
import com.qingmei2.sample.db.UserDatabase
import com.qingmei2.sample.http.RxSchedulers
import com.qingmei2.sample.http.entity.LoginUser
import com.qingmei2.sample.http.service.ServiceManager
import io.reactivex.Completable
import io.reactivex.Flowable

class LoginDataSourceRepository(
        remoteDataSource: ILoginRemoteDataSource,
        localDataSource: ILoginLocalDataSource
) : RhineRepositoryBoth<ILoginRemoteDataSource, ILoginLocalDataSource>(remoteDataSource, localDataSource) {

    fun login(username: String, password: String): Flowable<LoginUser> =
            remoteDataSource.login(username, password)
                    .doOnNext { UserManager.INSTANCE = it }
                    .flatMap {
                        localDataSource.savePrefsUser(username, password)  // save user
                                .andThen(Flowable.just(it))
                    }

    fun prefsUser(): Flowable<LoginEntity> = localDataSource.fetchPrefsUser()
}

class LoginRemoteDataSource(
        private val serviceManager: ServiceManager
) : ILoginRemoteDataSource {

    override fun login(username: String, password: String): Flowable<LoginUser> =
            serviceManager.loginService
                    .login(username, password)
                    .subscribeOn(RxSchedulers.io)

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

    override fun fetchPrefsUser(): Flowable<LoginEntity> =
            Flowable.just(prefs)
                    .map { LoginEntity(1, it.username, it.password) }
}