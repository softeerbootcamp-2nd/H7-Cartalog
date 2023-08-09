package com.softeer.cartalog.data.repository

import com.softeer.cartalog.data.repository.local.CarLocalDataSource
import com.softeer.cartalog.data.repository.remote.CarRemoteDataSource

class CarRepositoryImpl(
    private val carLocalDataSource: CarLocalDataSource,
    private val carRemoteDataSource: CarRemoteDataSource
) : CarRepository {

}