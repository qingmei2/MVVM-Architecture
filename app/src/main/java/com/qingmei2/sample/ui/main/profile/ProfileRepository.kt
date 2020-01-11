package com.qingmei2.sample.ui.main.profile

import com.qingmei2.architecture.core.base.repository.BaseRepositoryRemote
import com.qingmei2.architecture.core.base.repository.IRemoteDataSource
import com.qingmei2.sample.http.service.ServiceManager

interface IRemoteProfileDataSource : IRemoteDataSource

class ProfileRepository(
        remoteDataSource: IRemoteProfileDataSource
) : BaseRepositoryRemote<IRemoteProfileDataSource>(remoteDataSource)

class ProfileRemoteDataSource(val serviceManager: ServiceManager) : IRemoteProfileDataSource