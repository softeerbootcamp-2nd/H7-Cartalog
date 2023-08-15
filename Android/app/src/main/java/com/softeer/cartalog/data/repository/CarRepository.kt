package com.softeer.cartalog.data.repository

import com.softeer.cartalog.data.model.Trim

interface CarRepository {

    suspend fun getTrims(): ArrayList<Trim>
}