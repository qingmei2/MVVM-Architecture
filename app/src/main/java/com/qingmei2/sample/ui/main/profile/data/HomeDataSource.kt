package com.qingmei2.sample.ui.main.profile.data

import com.qingmei2.sample.base.BaseRepositoryRemote
import com.qingmei2.sample.http.service.ServiceManager

class ProfileRepository(
        remoteDataSource: IRemoteProfileDataSource
) : BaseRepositoryRemote<IRemoteProfileDataSource>(remoteDataSource)

class ProfileRemoteDataSource(val serviceManager: ServiceManager) : IRemoteProfileDataSource