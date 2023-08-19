package com.softeer.cartalog.data.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.softeer.cartalog.data.enums.PriceDataType

@Entity
data class PriceData(
    val carId: Int,
    val optionType: PriceDataType,
    val optionId: Int?,
    val name: String,
    val price: Int,
    val imgUrl: String?,
    val colorCode: String?,
    @PrimaryKey (autoGenerate = true) var id: Int = 0
) {

}