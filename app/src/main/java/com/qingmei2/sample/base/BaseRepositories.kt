package com.qingmei2.sample.base

import com.qingmei2.rhine.base.repository.*

open class BaseRepositoryBoth<T : IRemoteDataSource, R : ILocalDataSource>(
        remoteDataSource: T,
        localDataSource: R
) : RhineRepositoryBoth<T, R>(remoteDataSource, localDataSource)

open class BaseRepositoryLocal<T : ILocalDataSource>(
        localDataSource: T
) : RhineRepositoryLocal<T>(localDataSource)

open class BaseRepositoryRemote<T : IRemoteDataSource>(
        remoteDataSource: T
) : RhineRepositoryRemote<T>(remoteDataSource)

open class BaseRepositoryNothing() : RhineRepositoryNothing()