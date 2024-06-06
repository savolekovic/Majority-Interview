package com.vosaa.majoritytechassignment.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Currency(
    val name: String = "",
    val symbol: String= "",
)