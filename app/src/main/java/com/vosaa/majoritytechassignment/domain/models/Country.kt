package com.vosaa.majoritytechassignment.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val flags: Flags,
    val name: Name,
    val population: Int = -1,
    val capital: List<String> = emptyList(),
    val currencies: List<Currency> = emptyList(),
    val region: String = ""
)