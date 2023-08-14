package com.softeer.cartalog.data.repository

import android.graphics.drawable.Drawable
import com.softeer.cartalog.data.model.Trims

interface CarRepository {

    suspend fun getTrims(): Trims
}