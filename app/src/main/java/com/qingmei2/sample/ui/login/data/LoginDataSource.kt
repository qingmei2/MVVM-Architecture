package com.qingmei2.sample.ui.login.data

import com.qingmei2.sample.http.RxSchedulers
import com.qingmei2.sample.http.entity.LoginUser
import com.qingmei2.sample.http.service.ServiceManager
import io.reactivex.Single

class LoginRemoteDataSource(
        private val serviceManager: ServiceManager
) : ILoginRemoteDataSource {

    override fun login(username: String,
                       password: String): Single<LoginUser> = serviceManager.loginService
            .login(username, password)
            .subscribeOn(RxSchedulers.io)

}

//class LoginLocalDataSource(
//        private val database: UserDatabase
//) : ILoginLocalDataSource {
//
//    override fun insertUser(user: LoginUser): Completable = database
//            .loginDao()
//            .insert()
//
//    override fun findUserByUsername(username: String): Maybe<LoginUser> {
//
//    }
//}