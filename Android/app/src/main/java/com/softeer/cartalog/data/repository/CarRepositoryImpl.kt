package com.softeer.cartalog.data.repository

import android.util.Log
import com.softeer.cartalog.data.model.Trim
import com.softeer.cartalog.data.model.TrimDetail
import com.softeer.cartalog.data.model.Type
import com.softeer.cartalog.data.repository.local.CarLocalDataSource
import com.softeer.cartalog.data.repository.remote.CarRemoteDataSource

class CarRepositoryImpl(
    private val carLocalDataSource: CarLocalDataSource,
    private val carRemoteDataSource: CarRemoteDataSource
) : CarRepository {

    override suspend fun getTrims(): List<Trim> {
        val response = carRemoteDataSource.getTrims()
        return if (response.isSuccessful) response.body()!!.trims else arrayListOf()
    }

    override suspend fun getTypes(): List<Type> {
        val response = carRemoteDataSource.getTypes()
        return if (response.isSuccessful) response.body()!!.modelTypes else arrayListOf()
    }

    override suspend fun getTrimDetail(modelTypeIds: String, trimId: Int): TrimDetail {
        val response = carRemoteDataSource.getTrimsDetail(modelTypeIds, trimId)
        return if (response.isSuccessful) response.body()!! else TrimDetail(0, 0, 0f)
    }
}