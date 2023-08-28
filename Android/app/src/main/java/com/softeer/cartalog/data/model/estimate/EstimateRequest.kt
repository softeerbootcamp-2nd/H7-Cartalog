package com.softeer.cartalog.data.model.estimate

data class EstimateRequest(
    val detailTrimId: Int,
    val exteriorColorCode: String,
    val interiorColorCode: String,
    val selectOptionOrPackageIds: List<String>
)
