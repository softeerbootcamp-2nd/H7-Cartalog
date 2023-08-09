package com.softeer.cartalog.data.model

data class TypeOption(
    val id: Int,
    val name: String,
    val price: Int,
    val chosen: Int,
    val imageUrl: String,
    val description: String,
    val hmgData: List<TypeHmgData>?
)
