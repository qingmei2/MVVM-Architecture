package com.qingmei2.rhine.base.repository

open class RhineRepositoryBoth<T : IRemoteDataSource, R : ILocalDataSource>(
        val remoteDataSource: T,
        val localDataSource: R
) : IRepository

open class RhineRepositoryLocal<T : ILocalDataSource>(
        val remoteDataSource: T
) : IRepository

open class RhineRepositoryRemote<T : IRemoteDataSource>(
        val remoteDataSource: T
) : IRepository

open class RhineRepositoryNothing() : IRepository