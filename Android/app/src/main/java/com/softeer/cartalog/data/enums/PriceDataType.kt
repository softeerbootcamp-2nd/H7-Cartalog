package com.softeer.cartalog.data.enums

enum class PriceDataType (val typeName: String) {
    POWERTRAIN("파워트레인/성능"),
    BODYTYPE("바디타입"),
    WHEELDRIVE("구동방식"),
    EXTERIOR_COLOR("외장색상"),
    INTERIOR_COLOR("내장색상"),
    OPTION("옵션");

    companion object {
        private val typeMap = values().associateBy(PriceDataType::typeName)

        fun fromTypeName(typeName: String): PriceDataType {
            return typeMap[typeName] ?: throw IllegalArgumentException("Unknown typeName: $typeName")
        }
    }
}