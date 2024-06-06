package com.vosaa.majoritytechassignment.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val flags: Flags,
    val name: Name
)