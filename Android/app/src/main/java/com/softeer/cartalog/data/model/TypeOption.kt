package com.softeer.cartalog.data.model

import java.io.Serializable

data class TypeOption(
    val id: Int,
    val name: String,
    val price: Int,
    val chosen: Int,
    val imageUrl: String,
    val description: String,
    val hmgData: List<TypeHmgData>?
) : Serializable
