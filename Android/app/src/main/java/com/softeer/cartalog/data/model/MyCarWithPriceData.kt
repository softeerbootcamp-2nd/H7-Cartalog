package com.softeer.cartalog.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class MyCarWithPriceData(
    @Embedded val myCar: MyCar,
    @Relation(
        parentColumn = "modelId",
        entityColumn = "carId"
    )
    val priceDataList: List<PriceData>
)
