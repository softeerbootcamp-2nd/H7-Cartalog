package com.softeer.cartalog.data.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyCar(
    val carName: String,
    val trim: String,
    val minPrice: Int,
    val exteriorImg: String,
    val interiorImg: String,
    val fuelEfficiency: Float?,
    val displacement: Int?
) {
    @PrimaryKey var id: Int = 1
}

