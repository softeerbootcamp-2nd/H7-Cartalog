package com.softeer.cartalog.data.model

data class Options(
    val multipleSelectParentCategory: List<String>,
    val selectOptions: List<Option>,
    val defaultOptions: List<Option>
)
