package com.softeer.cartalog.data.repository

import com.softeer.cartalog.data.model.CarColor
import com.softeer.cartalog.data.model.ExteriorColors
import com.softeer.cartalog.data.model.Trims
import com.softeer.cartalog.data.model.Trim
import com.softeer.cartalog.data.model.TrimDetail
import com.softeer.cartalog.data.model.Type

interface CarRepository {

    suspend fun getTrims(): Trims
    suspend fun setInitialMyCarData(carName: String, trim: Trim)    
    suspend fun getTypes(): List<Type>
    suspend fun getTrimDetail(modelTypeIds: String, trimId: Int): TrimDetail
    suspend fun getCarColors(isExterior: Boolean, trimId: Int): List<CarColor>

}