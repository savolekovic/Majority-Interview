package com.vosaa.majoritytechassignment.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Name(
    val common: String = "",
    val official: String = "",
)