package com.qingmei2.sample.ui.login

import com.qingmei2.architecture.core.base.repository.BaseRepositoryBoth
import com.qingmei2.architecture.core.base.repository.ILocalDataSource
import com.qingmei2.architecture.core.base.repository.IRemoteDataSource
import com.qingmei2.sample.base.Results
import com.qingmei2.sample.db.UserDatabase
import com.qingmei2.sample.entity.UserInfo
import com.qingmei2.sample.http.service.ServiceManager
import com.qingmei2.sample.http.service.bean.LoginRequestModel
import com.qingmei2.sample.manager.UserManager
import com.qingmei2.sample.repository.UserInfoRepository
import com.qingmei2.sample.utils.processApiResponse
import javax.inject.Inject

class LoginRepository @Inject constructor(
        remoteDataSource: LoginRemoteDataSource,
        localDataSource: LoginLocalDataSource
) : BaseRepositoryBoth<LoginRemoteDataSource, LoginLocalDataSource>(remoteDataSource, localDataSource) {

    suspend fun login(username: String, password: String): Results<UserInfo> {
        // 保存用户登录信息
        localDataSource.savePrefUser(username, password)
        val userInfo = remoteDataSource.login()

        // 如果登录失败，清除登录信息
        when (userInfo) {
            is Results.Failure -> localDataSource.clearPrefsUser()
            is Results.Success -> UserManager.INSTANCE = requireNotNull(userInfo.data)
        }
        return userInfo
    }

    fun fetchAutoLogin(): AutoLoginEvent {
        return localDataSource.fetchAutoLogin()
    }
}

class LoginRemoteDataSource @Inject constructor(
        private val serviceManager: ServiceManager
) : IRemoteDataSource {

    suspend fun login(): Results<UserInfo> {
        // 1.用户登录认证
        val userAccessTokenResult = processApiResponse {
            serviceManager.loginService.authorizations(LoginRequestModel.generate())
        }

        return when (userAccessTokenResult) {
            is Results.Success -> {
                // 2.获取用户详细信息
                processApiResponse(serviceManager.userService::fetchUserOwner)
            }
            is Results.Failure -> userAccessTokenResult
        }
    }
}

class LoginLocalDataSource @Inject constructor(
        private val db: UserDatabase,
        private val userRepository: UserInfoRepository
) : ILocalDataSource {

    fun savePrefUser(username: String, password: String) {
        userRepository.username = username
        userRepository.password = password
    }

    fun clearPrefsUser() {
        userRepository.username = ""
        userRepository.password = ""
    }

    fun fetchAutoLogin(): AutoLoginEvent {
        val username = userRepository.username
        val password = userRepository.password
        val isAutoLogin = userRepository.isAutoLogin
        return when (username.isNotEmpty() && password.isNotEmpty() && isAutoLogin) {
            true -> AutoLoginEvent(true, username, password)
            false -> AutoLoginEvent(false, "", "")
        }
    }
}

data class AutoLoginEvent(
        val autoLogin: Boolean,
        val username: String,
        val password: String
)
