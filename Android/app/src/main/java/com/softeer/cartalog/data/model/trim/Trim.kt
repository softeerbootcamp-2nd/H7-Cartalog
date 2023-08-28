package com.softeer.cartalog.data.model.trim

data class Trim(
    val id: Int,
    val name: String,
    val description: String,
    val minPrice: Int,
    val maxPrice: Int,
    val exteriorImageUrl: String,
    val interiorImageUrl: String,
    val wheelImageUrl: String,
    val hmgData: ArrayList<TrimHmgData?>?,
    val defaultInfo: DefaultInfo?
) {

}
