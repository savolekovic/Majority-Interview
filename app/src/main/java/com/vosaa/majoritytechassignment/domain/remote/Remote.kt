package com.vosaa.majoritytechassignment.domain.remote

import com.vosaa.majoritytechassignment.data.remote.ApiService
import com.vosaa.majoritytechassignment.data.remote.BaseRemote
import com.vosaa.majoritytechassignment.util.error.ErrorManager
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Remote @Inject constructor(
    private val apiService: ApiService,
    errorManager: ErrorManager,
) : BaseRemote(errorManager) {

    suspend fun getAllCountries() = parseResult{
        apiService.getAllCountries()
    }

    suspend fun getCountry(countryName: String) = parseResult {
        apiService.getCountry(countryName)
    }

}