package com.softeer.cartalog.data.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.softeer.cartalog.data.enums.PriceDataType
import com.softeer.cartalog.data.model.color.CarColor
import com.softeer.cartalog.data.model.db.MyCar
import com.softeer.cartalog.data.model.db.PriceData
import com.softeer.cartalog.data.model.estimate.EstimateCounts
import com.softeer.cartalog.data.model.estimate.EstimateRequest
import com.softeer.cartalog.data.model.estimate.SimilarEstimates
import com.softeer.cartalog.data.model.option.DetailOptions
import com.softeer.cartalog.data.model.option.Options
import com.softeer.cartalog.data.model.summary.SummaryCarImage
import com.softeer.cartalog.data.model.trim.Trim
import com.softeer.cartalog.data.model.trim.Trims
import com.softeer.cartalog.data.model.type.TrimDetail
import com.softeer.cartalog.data.model.type.Type
import com.softeer.cartalog.data.repository.local.CarLocalDataSource
import com.softeer.cartalog.data.repository.remote.CarRemoteDataSource
import okio.ByteString.Companion.encodeUtf8


class CarRepositoryImpl(
    private val carLocalDataSource: CarLocalDataSource,
    private val carRemoteDataSource: CarRemoteDataSource
) : CarRepository {

    override suspend fun getTrims(): Trims {
        val response = carRemoteDataSource.getTrims()
        return if (response.isSuccessful) response.body()!! else Trims("", emptyList())
    }

    override suspend fun setInitialMyCarData(carName: String, trim: Trim) {

        val myCar =
            MyCar(
                carName,
                trim.name,
                trim.minPrice,
                trim.exteriorImageUrl,
                trim.interiorImageUrl,
                null,
                null
            )
        val carId = carLocalDataSource.setInitialMyCar(myCar)
        val priceDataList = mutableListOf<PriceData>()

        if (carLocalDataSource.isEmpty(carId)) {
            trim.defaultInfo?.run {
                modelTypes.forEach {
                    priceDataList.add(
                        PriceData(
                            carId,
                            PriceDataType.fromTypeName(it.type),
                            it.option.id,
                            it.option.name,
                            it.option.price,
                            null,
                            null
                        )
                    )
                }

                exteriorColor.run {
                    priceDataList.add(
                        PriceData(
                            carId,
                            PriceDataType.EXTERIOR_COLOR,
                            null,
                            name,
                            price,
                            null,
                            code
                        )
                    )
                }

                interiorColor.run {
                    priceDataList.add(
                        PriceData(
                            carId,
                            PriceDataType.INTERIOR_COLOR,
                            null,
                            name,
                            price,
                            null,
                            code
                        )
                    )
                }

            }
            carLocalDataSource.setInitialPriceData(priceDataList.toList())
        }
    }

    override suspend fun getTypes(): List<Type> {
        val response = carRemoteDataSource.getTypes()
        return if (response.isSuccessful) response.body()!!.modelTypes else emptyList()
    }

    override suspend fun getTrimDetail(modelTypeIds: String, trimId: Int): TrimDetail {
        val response = carRemoteDataSource.getTrimsDetail(modelTypeIds, trimId)
        return if (response.isSuccessful) response.body()!! else TrimDetail(0, 0, 0f)
    }

    override suspend fun getMyCarData(): MyCar? {
        return carLocalDataSource.getMyCar()
    }

    override suspend fun getPriceDataList(): List<PriceData> {
        return carLocalDataSource.getPriceDataList()
    }

    override suspend fun getSummaryCarImage(exterior: String, interior: String): SummaryCarImage {
        val response = carRemoteDataSource.getSummaryCarImage(exterior, interior)
        return if (response.isSuccessful) response.body()!! else SummaryCarImage("", "")
    }

    override suspend fun getCarColors(
        isExterior: Boolean,
        trimId: Int,
        exteriorColorCode: String
    ): List<CarColor> {
        return if (isExterior) {
            val response = carRemoteDataSource.getExteriorColors(trimId)
            if (response.isSuccessful) response.body()!!.exteriorColors else emptyList()
        } else {
            val response = carRemoteDataSource.getInteriorColors(exteriorColorCode, trimId)
            if (response.isSuccessful) response.body()!!.interiorColors else emptyList()
        }
    }

    override suspend fun getOptions(
        detailTrimId: Int,
        interiorColorCode: String
    ): Options {
        val response = carRemoteDataSource.getOptions(detailTrimId, interiorColorCode)
        return if (response.isSuccessful) response.body()!! else Options(
            emptyList(),
            emptyList(),
            emptyList()
        )
    }

    override suspend fun getDetailOptions(
        optionId: String
    ): DetailOptions {
        val response = carRemoteDataSource.getDetailOptions(optionId)
        return if (response.isSuccessful) response.body()!! else DetailOptions(
            "", "", emptyList(), emptyList(), "", emptyList(), false
        )
    }

    override suspend fun saveUserTypeData(
        powerTrain: PriceData,
        bodyType: PriceData,
        wheelDrive: PriceData
    ) {
        carLocalDataSource.updatePriceData(powerTrain)
        carLocalDataSource.updatePriceData(bodyType)
        carLocalDataSource.updatePriceData(wheelDrive)
    }

    override suspend fun getTypeData(type: PriceDataType): PriceData {
        return carLocalDataSource.getPriceData(type)
    }

    override suspend fun saveUserColorData(color: PriceData) {
        carLocalDataSource.updatePriceData(color)
    }

    override suspend fun saveUserCarData(myCar: MyCar) {
        carLocalDataSource.updateMyCarData(myCar)
    }

    override suspend fun getOptionTypeDataList(): List<PriceData> {
        return carLocalDataSource.getOptionTypeDataList()
    }

    override suspend fun setOptionTypeDataList(optionList: List<PriceData>): List<Long> {
        return carLocalDataSource.insertOptionDataList(optionList)
    }

    override suspend fun deleteOptionItem(option: PriceData) {
        carLocalDataSource.deleteOptionItem(option)
    }

    override suspend fun postEstimate(estimate: EstimateRequest): Int {
//        val json = Gson().toJson(estimate)
        val response = carRemoteDataSource.postEstimate(estimate)
        Log.d("TESTER", "post ${response}")
        return if (response.isSuccessful) response.body()!! else 0
    }

    override suspend fun getEstimateCount(estimateId: Int): EstimateCounts {
        val response = carRemoteDataSource.getEstimateCount(estimateId)
        return if (response.isSuccessful) response.body()!! else EstimateCounts(0, emptyList())
    }

    override suspend fun getSimilarEstimate(estimateId: Int, similarEstimateId: Int): SimilarEstimates? {
        val response = carRemoteDataSource.getSimilarEstimate(estimateId, similarEstimateId)
        return if (response.isSuccessful) response.body()!! else null
    }



}