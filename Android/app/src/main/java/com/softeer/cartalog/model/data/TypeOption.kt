package com.softeer.cartalog.model.data

data class TypeOption(
    val id: Int,
    val name: String,
    val price: Int,
    val chosen: Int,
    val imageUrl: String,
    val description: String,
    val powerTrainHMGData: List<TypeHmgData>
)
