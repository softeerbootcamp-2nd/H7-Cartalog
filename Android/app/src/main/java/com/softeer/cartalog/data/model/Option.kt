package com.softeer.cartalog.data.model

data class Option(
    val id: Int,
    val name: String,
    val parentCategory: String?,
    val childCategory: String?,
    val imageUrl: String,
    val price: Int,
    val chosen: Int,
    val hashTags: List<String>
)
