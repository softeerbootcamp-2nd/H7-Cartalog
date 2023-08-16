package com.softeer.cartalog.data.repository.remote

import com.softeer.cartalog.data.model.TrimDetail
import com.softeer.cartalog.data.model.Trims
import com.softeer.cartalog.data.model.Types
import com.softeer.cartalog.data.remote.api.CarApi
import retrofit2.Response
import retrofit2.http.Query

class CarRemoteDataSource(
    private val carApi: CarApi
) {
    suspend fun getTrims(): Response<Trims> {
        return carApi.getTrims(1)
    }

    suspend fun getTypes(): Response<Types> {
        return carApi.getTypes(1)
    }

    suspend fun getTrimsDetail(
        modelTypeIds: String,
        trimId: Int
    ): Response<TrimDetail> {
        return carApi.getTrimsDetail(modelTypeIds, trimId)
    }

}