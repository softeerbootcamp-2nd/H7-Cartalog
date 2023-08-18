package com.softeer.cartalog.data.repository

import com.softeer.cartalog.data.model.SummaryCarImage
import com.softeer.cartalog.data.model.CarColor
import com.softeer.cartalog.data.model.Options
import com.softeer.cartalog.data.model.Trims
import com.softeer.cartalog.data.model.Trim
import com.softeer.cartalog.data.model.TrimDetail
import com.softeer.cartalog.data.model.Type
import com.softeer.cartalog.data.model.db.MyCar
import com.softeer.cartalog.data.model.db.PriceData
import retrofit2.Response

interface CarRepository {

    suspend fun getTrims(): Trims
    suspend fun setInitialMyCarData(carName: String, trim: Trim)
    suspend fun getTypes(): List<Type>
    suspend fun getTrimDetail(modelTypeIds: String, trimId: Int): TrimDetail
    suspend fun getMyCarData(): MyCar
    suspend fun getPirceDataList(): List<PriceData>
    suspend fun getSummaryCarImage(exterior: String, interior: String): SummaryCarImage
    suspend fun getCarColors(isExterior: Boolean, trimId: Int, exteriorColorCode: String): List<CarColor>
    suspend fun getOptions(detailTrimId: Int, interiorColorCode: String): Options
}