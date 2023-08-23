package com.softeer.cartalog.data.model

data class CarColor(
    val code: String,
    val name: String,
    val colorImageUrl: String,
    val carImageDirectory: String?,
    val carImageUrl: String?,
    val price: Int,
    val chosen: Int,
    var isSelected: Boolean
)
