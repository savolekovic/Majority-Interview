package com.vosaa.majoritytechassignment.domain.models

import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@Serializable
data class ResponseCountry(
    val countries: List<Country> = persistentListOf(),
)