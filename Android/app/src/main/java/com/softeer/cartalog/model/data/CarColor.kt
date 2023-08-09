package com.softeer.cartalog.model.data

data class CarColor(
    val id: String,
    val name: String,
    val colorImageUrl: String,
    val carImageUrl: String,
    val price: Int,
    val chosen: Int,
    var isSelected: Boolean
)
