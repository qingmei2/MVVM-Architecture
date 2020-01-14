package com.qingmei2.sample.ui.login

import com.qingmei2.architecture.core.base.repository.BaseRepositoryBoth
import com.qingmei2.architecture.core.base.repository.ILocalDataSource
import com.qingmei2.architecture.core.base.repository.IRemoteDataSource
import com.qingmei2.sample.db.UserDatabase
import com.qingmei2.sample.entity.Resource
import com.qingmei2.sample.entity.UserAccessToken
import com.qingmei2.sample.entity.UserInfo
import com.qingmei2.sample.http.Errors
import com.qingmei2.sample.http.service.ServiceManager
import com.qingmei2.sample.http.service.bean.LoginRequestModel
import com.qingmei2.sample.manager.UserManager
import com.qingmei2.sample.repository.UserInfoRepository
import retrofit2.Response
import java.io.IOException

class LoginRepository(
        remoteDataSource: LoginRemoteDataSource,
        localDataSource: LoginLocalDataSource
) : BaseRepositoryBoth<LoginRemoteDataSource, LoginLocalDataSource>(remoteDataSource, localDataSource) {

    suspend fun login(username: String, password: String): Resource<UserInfo> {
        // 保存用户登录信息
        localDataSource.savePrefUser(username, password)
        val userInfo = remoteDataSource.login()

        // 如果登录失败，清除登录信息
        if (userInfo is Resource.DataError<*>) {
            localDataSource.clearPrefsUser()
        } else {
            UserManager.INSTANCE = requireNotNull(userInfo.data)
        }
        return userInfo
    }

    fun fetchAutoLogin(): AutoLoginEvent {
        return localDataSource.fetchAutoLogin()
    }
}

class LoginRemoteDataSource(
        private val serviceManager: ServiceManager
) : IRemoteDataSource {

    suspend fun login(): Resource<UserInfo> {
        // 1.用户登录认证
        val userAccessTokenResponse =
                serviceManager.loginService.authorizations(LoginRequestModel.generate())
        val response = processCallApi(userAccessTokenResponse)

        if (response !is UserAccessToken) {
            return Resource.DataError(error = response as Errors)
        }

        // 2.获取用户详细信息
        val userInfoResponse = serviceManager.userService.fetchUserOwner()
        val userInfo = processCallApi(userInfoResponse)

        return if (userInfo !is UserInfo) {
            Resource.DataError(error = response as Errors)
        } else {
            Resource.Success(userInfo)
        }
    }

    private fun processCallApi(response: Response<*>): Any? {
        return try {
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                Errors.NetworkError
            }
        } catch (e: IOException) {
            Errors.NetworkError
        }
    }
}

class LoginLocalDataSource(
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