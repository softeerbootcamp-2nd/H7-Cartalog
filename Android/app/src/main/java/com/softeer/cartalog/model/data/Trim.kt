package com.softeer.cartalog.model.data

data class Trim(
    val trimName: String,
    val description: String,
    val startPrice: Int,
    val options: List<TrimOption>
) {

}
