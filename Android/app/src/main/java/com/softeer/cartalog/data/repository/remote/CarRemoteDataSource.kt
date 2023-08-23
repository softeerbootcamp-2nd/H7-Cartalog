package com.softeer.cartalog.data.repository.remote

import com.softeer.cartalog.data.model.option.DetailOptions
import com.softeer.cartalog.data.model.summary.SummaryCarImage
import com.softeer.cartalog.data.model.color.ExteriorColors
import com.softeer.cartalog.data.model.color.InteriorColors
import com.softeer.cartalog.data.model.estimate.EstimateCounts
import com.softeer.cartalog.data.model.estimate.EstimateRequest
import com.softeer.cartalog.data.model.estimate.SimilarEstimates
import com.softeer.cartalog.data.model.option.Options
import com.softeer.cartalog.data.model.type.TrimDetail
import com.softeer.cartalog.data.model.trim.Trims
import com.softeer.cartalog.data.model.type.Types
import com.softeer.cartalog.data.remote.api.CarApi
import retrofit2.Response

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

    suspend fun getSummaryCarImage(exterior: String, interior: String): Response<SummaryCarImage> {
        return carApi.getCarSummaryImage(exterior, interior)
    }

    suspend fun getExteriorColors(trimId: Int): Response<ExteriorColors> {
        return carApi.getExteriorColors(trimId)
    }

    suspend fun getInteriorColors(
        exteriorColorCode: String,
        trimId: Int
    ): Response<InteriorColors> {
        return carApi.getInteriorColors(exteriorColorCode, trimId)
    }

    suspend fun getOptions(
        detailTrimId: Int,
        interiorColorCode: String
    ): Response<Options> {
        return carApi.getOptions(detailTrimId, interiorColorCode)
    }

    suspend fun getDetailOptions(optionId: String): Response<DetailOptions> {
        return carApi.getDetailOptions(optionId)
    }

    suspend fun postEstimate(estimate: EstimateRequest): Response<Int> {
        return carApi.postEstimate(estimate)
    }

    suspend fun getEstimateCount(estimateId: Int): Response<EstimateCounts> {
        return carApi.getEstimateCount(estimateId)
    }

    suspend fun getSimilarEstimate(
        estimateId: Int,
        similarEstimateId: Int
    ): Response<SimilarEstimates> {
        return carApi.getSimilarEstimate(estimateId, similarEstimateId)
    }

}