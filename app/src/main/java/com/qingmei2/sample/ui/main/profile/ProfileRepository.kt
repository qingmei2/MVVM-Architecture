package com.qingmei2.sample.ui.main.profile

import com.qingmei2.architecture.core.base.repository.BaseRepositoryRemote
import com.qingmei2.architecture.core.base.repository.IRemoteDataSource
import com.qingmei2.sample.http.service.ServiceManager
import javax.inject.Inject

interface IRemoteProfileDataSource : IRemoteDataSource

class ProfileRepository @Inject constructor(
        remoteDataSource: ProfileRemoteDataSource
) : BaseRepositoryRemote<IRemoteProfileDataSource>(remoteDataSource)

class ProfileRemoteDataSource @Inject constructor(
        val serviceManager: ServiceManager
) : IRemoteProfileDataSource
