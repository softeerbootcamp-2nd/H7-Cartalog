package com.softeer.cartalog.model.repository

import com.softeer.cartalog.model.repository.local.LocalDataSource
import com.softeer.cartalog.model.repository.remote.RemoteDataSource

class DataRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : DataRepository {

}