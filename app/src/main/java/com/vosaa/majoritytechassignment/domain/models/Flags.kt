package com.vosaa.majoritytechassignment.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Flags(
    val alt: String = "",
    val png: String= "",
    val svg: String= ""
)