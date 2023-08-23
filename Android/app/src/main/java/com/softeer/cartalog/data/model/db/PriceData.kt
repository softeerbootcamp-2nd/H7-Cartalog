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
    val code: String?,
    val isCheckedFromEstimate: Boolean = false,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PriceData) return false

        return carId == other.carId &&
                optionType == other.optionType &&
                optionId == other.optionId &&
                name == other.name &&
                price == other.price &&
                imgUrl == other.imgUrl &&
                code == other.code &&
                isCheckedFromEstimate == other.isCheckedFromEstimate
    }

    override fun hashCode(): Int {
        var result = carId
        result = 31 * result + optionType.hashCode()
        result = 31 * result + (optionId ?: 0)
        result = 31 * result + name.hashCode()
        result = 31 * result + price
        result = 31 * result + (imgUrl?.hashCode() ?: 0)
        result = 31 * result + (code?.hashCode() ?: 0)
        result = 31 * result + isCheckedFromEstimate.hashCode()
        return result
    }
}