package com.vosaa.majoritytechassignment.data.remote

import com.vosaa.majoritytechassignment.domain.models.Country
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("all?fields=name,flags")
    suspend fun getAllCountries(): Response<ArrayList<Country>>

    @GET("name/{name}?fullText=true")
    suspend fun getCountry(
        @Path("name") name: String,
    ): Response<Country>

}