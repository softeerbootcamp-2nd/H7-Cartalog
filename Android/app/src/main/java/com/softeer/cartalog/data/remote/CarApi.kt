package com.softeer.cartalog.data.remote.api

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
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface CarApi {

    @GET("models/types")
    suspend fun getTypes(@Query("trimId") trimId: Int): Response<Types>

    @GET("/models/trims/detail")
    suspend fun getTrimsDetail(
        @Query("modelTypeIds") modelTypeIds: String,
        @Query("trimId") trimId: Int
    ): Response<TrimDetail>

    @GET("/models/trims")
    suspend fun getTrims(@Query("basicModelId") basicModelId: Int): Response<Trims>

    @GET("/models/images")
    suspend fun getCarSummaryImage(
        @Query("exteriorColorCode") exteriorColor: String,
        @Query("interiorColorCode") interiorColor: String
    ): Response<SummaryCarImage>

    @GET("/models/trims/exterior-colors")
    suspend fun getExteriorColors(@Query("trimId") trimId: Int): Response<ExteriorColors>

    @GET("/models/trims/interior-colors")
    suspend fun getInteriorColors(
        @Query("exteriorColorCode") exteriorColorCode: String,
        @Query("trimId") trimId: Int
    ): Response<InteriorColors>

    @GET("/models/trims/options")
    suspend fun getOptions(
        @Query("detailTrimId") detailTrimId: Int,
        @Query("interiorColorCode") interiorColorCode: String
    ): Response<Options>

    @GET("/models/trims/options/detail")
    suspend fun getDetailOptions(
        @Query("optionId") optionId: String,
    ): Response<DetailOptions>

    @Headers("accept: */*", "Content-Type: application/json")
    @POST("/estimates")
    suspend fun postEstimate(@Body estimateRequestDto: EstimateRequest): Response<Int>

    @GET("/similarity")
    suspend fun getEstimateCount(@Query("estimateId") estimateId: Int): Response<EstimateCounts>

    @GET("/similarity/releases")
    suspend fun getSimilarEstimate(
        @Query("estimateId") estimateId: Int,
        @Query("similarEstimateId") similarEstimateId: Int
    ): Response<SimilarEstimates>
}