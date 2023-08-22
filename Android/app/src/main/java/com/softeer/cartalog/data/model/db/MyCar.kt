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
    val displacement: Int?,
    val totalPrice: Int = 0
) {
    @PrimaryKey var id: Int = 1
}

