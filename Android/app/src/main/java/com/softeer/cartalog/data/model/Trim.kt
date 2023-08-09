package com.softeer.cartalog.data.model

data class Trim(
    val trimName: String,
    val description: String,
    val startPrice: Int,
    val options: List<TrimOption>
) {

}
