package com.qingmei2.sample.ui.main.profile

import com.qingmei2.rhine.base.repository.BaseRepositoryRemote
import com.qingmei2.rhine.base.repository.IRemoteDataSource
import com.qingmei2.sample.http.service.ServiceManager

interface IRemoteProfileDataSource : IRemoteDataSource

class ProfileRepository(
        remoteDataSource: IRemoteProfileDataSource
) : BaseRepositoryRemote<IRemoteProfileDataSource>(remoteDataSource)

class ProfileRemoteDataSource(val serviceManager: ServiceManager) : IRemoteProfileDataSource