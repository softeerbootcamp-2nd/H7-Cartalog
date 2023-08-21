package com.softeer.cartalog.data.remote.api

import com.softeer.cartalog.data.model.DetailOptions
import com.softeer.cartalog.data.model.SummaryCarImage
import com.softeer.cartalog.data.model.ExteriorColors
import com.softeer.cartalog.data.model.InteriorColors
import com.softeer.cartalog.data.model.Options
import com.softeer.cartalog.data.model.TrimDetail
import com.softeer.cartalog.data.model.Trims
import com.softeer.cartalog.data.model.Types
import retrofit2.Response
import retrofit2.http.GET
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
}