package com.softeer.cartalog.data.repository

import com.softeer.cartalog.data.model.Trim
import com.softeer.cartalog.data.model.Trims

interface CarRepository {

    suspend fun getTrims(): Trims
    suspend fun setInitialMyCarData(carName: String, trim: Trim)
}