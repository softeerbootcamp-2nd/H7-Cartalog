package com.softeer.cartalog.data.model.option

import com.google.gson.annotations.SerializedName
import com.softeer.cartalog.data.model.trim.TrimHmgData

class DetailOptions(
    val name: String,
    val imageUrl: String,
    val hashTags: List<String>?,
    val options: List<DetailOptions>?,
    val description: String,
    val hmgData: List<TrimHmgData>?,
    @SerializedName("package") val isPackage: Boolean
)