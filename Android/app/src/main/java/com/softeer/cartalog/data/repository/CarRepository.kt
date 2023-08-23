package com.softeer.cartalog.data.repository

import com.softeer.cartalog.data.enums.PriceDataType
import com.softeer.cartalog.data.model.color.CarColor
import com.softeer.cartalog.data.model.option.DetailOptions
import com.softeer.cartalog.data.model.option.Options
import com.softeer.cartalog.data.model.summary.SummaryCarImage
import com.softeer.cartalog.data.model.trim.Trim
import com.softeer.cartalog.data.model.type.TrimDetail
import com.softeer.cartalog.data.model.trim.Trims
import com.softeer.cartalog.data.model.type.Type
import com.softeer.cartalog.data.model.db.MyCar
import com.softeer.cartalog.data.model.db.PriceData
import com.softeer.cartalog.data.model.estimate.EstimateCounts
import com.softeer.cartalog.data.model.estimate.EstimateRequest
import com.softeer.cartalog.data.model.estimate.SimilarEstimates
import retrofit2.Response

interface CarRepository {

    suspend fun getTrims(): Trims
    suspend fun setInitialMyCarData(carName: String, trim: Trim)
    suspend fun getTypes(): List<Type>
    suspend fun getTrimDetail(modelTypeIds: String, trimId: Int): TrimDetail
    suspend fun getMyCarData(): MyCar?
    suspend fun getPriceDataList(): List<PriceData>
    suspend fun getSummaryCarImage(exterior: String, interior: String): SummaryCarImage
    suspend fun getCarColors(isExterior: Boolean, trimId: Int, exteriorColorCode: String): List<CarColor>
    suspend fun getOptions(detailTrimId: Int, interiorColorCode: String): Options
    suspend fun getDetailOptions(optionId: String): DetailOptions
    suspend fun saveUserTypeData(powerTrain: PriceData, bodyType: PriceData, wheelDrive: PriceData)
    suspend fun getTypeData(type: PriceDataType): PriceData
    suspend fun saveUserColorData(color: PriceData)
    suspend fun saveUserCarData(myCar: MyCar)
    suspend fun getOptionTypeDataList(): List<PriceData>
    suspend fun setOptionTypeDataList(optionList: List<PriceData>): List<Long>
    suspend fun deleteOptionItem(option: PriceData)
    suspend fun postEstimate(estimate: EstimateRequest): Int
    suspend fun getEstimateCount(estimateId: Int): EstimateCounts
    suspend fun getSimilarEstimate(estimateId: Int, similarEstimateId: Int): SimilarEstimates?
}