package com.softeer.cartalog.data.model.db

import androidx.room.Embedded
import androidx.room.Relation

data class MyCarWithPriceData(
    @Embedded val myCar: MyCar,
    @Relation(
        parentColumn = "id",
        entityColumn = "carId"
    )
    val priceDataList: List<PriceData>
)
