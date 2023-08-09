package com.softeer.cartalog.data.model

data class CarColor(
    val id: String,
    val name: String,
    val colorImageUrl: String,
    val carImageUrl: String,
    val price: Int,
    val chosen: Int,
    var isSelected: Boolean
)
