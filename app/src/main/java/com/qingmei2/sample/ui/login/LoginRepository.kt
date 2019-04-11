package com.qingmei2.sample.ui.login

import arrow.core.Either
import arrow.core.Tuple2
import com.qingmei2.rhine.base.repository.BaseRepositoryBoth
import com.qingmei2.rhine.base.repository.ILocalDataSource
import com.qingmei2.rhine.base.repository.IRemoteDataSource
import com.qingmei2.rhine.util.RxSchedulers
import com.qingmei2.sample.db.UserDatabase
import com.qingmei2.sample.entity.UserInfo
import com.qingmei2.sample.http.Errors
import com.qingmei2.sample.http.service.ServiceManager
import com.qingmei2.sample.http.service.bean.LoginRequestModel
import com.qingmei2.sample.manager.UserManager
import com.qingmei2.sample.repository.UserInfoRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class LoginRepository(
        remoteDataSource: LoginRemoteDataSource,
        localDataSource: LoginLocalDataSource
) : BaseRepositoryBoth<LoginRemoteDataSource, LoginLocalDataSource>(remoteDataSource, localDataSource) {

    fun login(username: String, password: String): Flowable<Either<Errors, UserInfo>> {
        // 保存用户登录信息
        return localDataSource.savePrefsUser(username, password)
                .andThen(remoteDataSource.login())
                .doOnNext { either ->
                    either.fold({
                        // 如果登录失败，清除登录信息
                        localDataSource.clearPrefsUser()
                        Unit
                    }, {
                        UserManager.INSTANCE = it
                    })
                }
                // 如果登录失败，清除登录信息
                .doOnError { localDataSource.clearPrefsUser() }
    }

    fun prefsUser(): Flowable<Either<Errors, Tuple2<String, String>>> {
        return localDataSource.fetchPrefsUser()
    }

    fun prefsAutoLogin(): Single<Boolean> {
        return localDataSource.isAutoLogin()
    }
}

class LoginRemoteDataSource(
        private val serviceManager: ServiceManager
) : IRemoteDataSource {

    fun login(): Flowable<Either<Errors, UserInfo>> {
        val authObservable = serviceManager.loginService
                .authorizations(LoginRequestModel.generate())

        val ownerInfoObservable = serviceManager.userService
                .fetchUserOwner()

        return authObservable                       // 1.用户登录认证
                .flatMap { ownerInfoObservable }    // 2.获取用户详细信息
                .subscribeOn(RxSchedulers.io)
                .map {
                    Either.right(it)
                }
    }
}

class LoginLocalDataSource(
        private val db: UserDatabase,
        private val userRepository: UserInfoRepository
) : ILocalDataSource {

    fun isAutoLogin(): Single<Boolean> {
        return Single.just(userRepository.isAutoLogin)
    }

    fun savePrefsUser(username: String, password: String): Completable {
        return Completable.fromAction {
            userRepository.username = username
            userRepository.password = password
        }
    }

    fun clearPrefsUser(): Completable {
        return Completable.fromAction {
            userRepository.username = ""
            userRepository.password = ""
        }
    }

    fun fetchPrefsUser(): Flowable<Either<Errors, Tuple2<String, String>>> =
            Flowable.just(userRepository).map {
                when (it.username.isNotEmpty() && it.password.isNotEmpty()) {
                    true -> Either.right(Tuple2(it.username, it.password))
                    false -> Either.left(Errors.EmptyResultsError)
                }
            }
}