package com.softeer.cartalog.data.model

import com.softeer.cartalog.data.model.db.PriceData

data class ConfirmDetail(
    val title: String,
    val contents: List<PriceData>?
)
