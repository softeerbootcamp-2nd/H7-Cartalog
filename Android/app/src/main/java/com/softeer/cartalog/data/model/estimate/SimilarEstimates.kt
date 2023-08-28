package com.softeer.cartalog.data.model.estimate

data class SimilarEstimates(
    val trimName           : String,
    val price              : Int,
    val modelTypes         : List<String>,
    val exteriorColorCode  : String,
    val interiorColorCode  : String,
    val nonExistentOptions : List<SimilarEstimateOption>
)
