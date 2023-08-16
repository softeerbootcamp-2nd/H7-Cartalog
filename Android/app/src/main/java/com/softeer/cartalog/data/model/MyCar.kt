package com.softeer.cartalog.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyCar(
    val carName: String,
    val modelName: String,
    val modelId: Int,
    val exteriorImg: String,
    val interiorImg: String
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}

