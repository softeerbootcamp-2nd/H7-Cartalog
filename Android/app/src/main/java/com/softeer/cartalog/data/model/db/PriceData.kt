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
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PriceData

        if (carId != other.carId) return false
        if (optionType != other.optionType) return false
        if (optionId != other.optionId) return false
        if (name != other.name) return false
        if (price != other.price) return false
        if (imgUrl != other.imgUrl) return false
        if (colorCode != other.colorCode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = carId
        result = 31 * result + optionType.hashCode()
        result = 31 * result + (optionId ?: 0)
        result = 31 * result + name.hashCode()
        result = 31 * result + price
        result = 31 * result + (imgUrl?.hashCode() ?: 0)
        result = 31 * result + (colorCode?.hashCode() ?: 0)
        return result
    }
}