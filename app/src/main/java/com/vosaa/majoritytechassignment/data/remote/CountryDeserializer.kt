package com.vosaa.majoritytechassignment.data.remote

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.vosaa.majoritytechassignment.domain.models.Country
import com.vosaa.majoritytechassignment.domain.models.Currency
import com.vosaa.majoritytechassignment.domain.models.Flags
import com.vosaa.majoritytechassignment.domain.models.Name
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryDeserializer @Inject constructor() : JsonDeserializer<Country> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext,
    ): Country {
        val jsonObject = json.asJsonObject

        // Deserialize the name field
        val nameJSONObject = jsonObject.getAsJsonObject("name")
        val common = nameJSONObject.get("common")?.asString ?: ""
        val official = nameJSONObject.get("official").asString

        // Deserialize the flags field
        val flags = context.deserialize<Flags>(jsonObject.get("flags"), Flags::class.java)

        // Deserialize the population field
        val population = jsonObject.get("population")?.asInt ?: -1

        // Deserialize the capital field
        val capitalJsonArray = jsonObject.getAsJsonArray("capital")
        val capital = capitalJsonArray?.map { it.asString } ?: emptyList()

        // Deserialize the currencies field
        val currenciesJsonObject = jsonObject.getAsJsonObject("currencies")
        val currencies = currenciesJsonObject?.entrySet()?.map { entry ->
            val currencyJsonObject = entry.value.asJsonObject
            Currency(
                name = currencyJsonObject.get("name")?.asString ?: "",
                symbol = currencyJsonObject.get("symbol")?.asString ?: ""
            )
        } ?: emptyList()


        // Deserialize the region field
        val region = jsonObject.get("region")?.asString ?: ""

        return Country(
            flags = flags,
            name = Name(common = common, official = official),
            population = population,
            capital = capital,
            currencies = currencies,
            region = region
        )
    }
}
