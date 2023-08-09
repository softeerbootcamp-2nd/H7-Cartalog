package com.softeer.cartalog.data.repository

import com.softeer.cartalog.data.repository.local.DataLocalDataSource
import com.softeer.cartalog.data.repository.remote.DataRemoteDataSource

class DataRepositoryImpl(
    private val dataLocalDataSource: DataLocalDataSource,
    private val dataRemoteDataSource: DataRemoteDataSource
) : DataRepository {

}