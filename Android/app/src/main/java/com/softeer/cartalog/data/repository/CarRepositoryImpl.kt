package com.softeer.cartalog.data.repository

import com.softeer.cartalog.data.enums.PriceDataType
import com.softeer.cartalog.data.model.MyCar
import com.softeer.cartalog.data.model.PriceData
import com.softeer.cartalog.data.model.Trim
import com.softeer.cartalog.data.model.Trims
import com.softeer.cartalog.data.repository.local.CarLocalDataSource
import com.softeer.cartalog.data.repository.remote.CarRemoteDataSource

class CarRepositoryImpl(
    private val carLocalDataSource: CarLocalDataSource,
    private val carRemoteDataSource: CarRemoteDataSource
) : CarRepository {

    override suspend fun getTrims(): Trims {
        val response = carRemoteDataSource.getTrims()
        return if (response.isSuccessful) response.body()!! else Trims("", arrayListOf())
    }

    override suspend fun setInitialMyCarData(carName: String, trim: Trim) {

        val myCar = MyCar(carName, trim.name, trim.id, trim.exteriorImageUrl, trim.interiorImageUrl)
        val carId = carLocalDataSource.setInitialMyCar(myCar)

        val priceDataList = mutableListOf<PriceData>()
        trim.defaultInfo?.modelTypes?.forEach {
            priceDataList.add(
                PriceData(
                    carId,
                    PriceDataType.fromTypeName(it.type),
                    it.option.id,
                    it.option.name,
                    it.option.price,
                    null
                )
            )
        }
        carLocalDataSource.setInitialPriceData(priceDataList.toList())
    }

}