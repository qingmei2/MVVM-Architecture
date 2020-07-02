package com.qingmei2.sample.di

import com.qingmei2.sample.http.service.LoginService
import com.qingmei2.sample.http.service.ServiceManager
import com.qingmei2.sample.http.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ServicesModule {

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Provides
    @Singleton
    fun provideServiceManager(userService: UserService, loginService: LoginService): ServiceManager {
        return ServiceManager(userService, loginService)
    }
}
