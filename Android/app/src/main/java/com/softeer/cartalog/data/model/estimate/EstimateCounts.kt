package com.softeer.cartalog.data.model.estimate

data class EstimateCounts(
    val myEstimateCount       : Int,
    val similarEstimateCounts : List<SimilarEstimateCounts>
)
