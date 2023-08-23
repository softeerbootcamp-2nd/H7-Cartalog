package com.softeer.cartalog.data.model.trim

import com.softeer.cartalog.data.model.confirm.ModelType

data class DefaultInfo(
    val modelTypes: ArrayList<ModelType>,
    val exteriorColor : DefaultColor,
    val interiorColor : DefaultColor
)
