package com.qingmei2.sample.ui.main.profile.data

import com.qingmei2.rhine.base.repository.RhineRepositoryRemote
import com.qingmei2.sample.http.service.ServiceManager

class ProfileRepository(
        remoteDataSource: IRemoteProfileDataSource
) : RhineRepositoryRemote<IRemoteProfileDataSource>(remoteDataSource)

class ProfileRemoteDataSource(val serviceManager: ServiceManager) : IRemoteProfileDataSource