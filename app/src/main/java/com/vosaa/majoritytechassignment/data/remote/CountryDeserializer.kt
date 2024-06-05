package com.vosaa.majoritytechassignment.data.remote

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.vosaa.majoritytechassignment.domain.models.Country
import com.vosaa.majoritytechassignment.domain.models.Currencies
import com.vosaa.majoritytechassignment.domain.models.Flags
import com.vosaa.majoritytechassignment.domain.models.Name
import com.vosaa.majoritytechassignment.domain.models.NativeName
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
        val common = nameJSONObject.get("common").asString
        val official = nameJSONObject.get("official").asString
        val name = Name(common = common, official = official)

        val flags = context.deserialize<Flags>(jsonObject.get("flags"), Flags::class.java)

//        val capital = context.deserialize<List<String>>(
//            jsonObject.get("capital"),
//            object : TypeToken<List<String>>() {}.type
//        )
//        val currencies =
//            context.deserialize<Currencies>(jsonObject.get("currencies"), Currencies::class.java)

//        val region = jsonObject.get("region").asString
//        val population = jsonObject.get("population").asInt

        return Country(
            flags = flags,
            name = name
        )
    }
}
