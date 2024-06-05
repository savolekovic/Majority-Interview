package com.vosaa.majoritytechassignment.domain.repository

import com.vosaa.majoritytechassignment.data.remote.BaseRepository
import com.vosaa.majoritytechassignment.domain.remote.Remote
import com.vosaa.majoritytechassignment.util.NetManager
import com.vosaa.majoritytechassignment.util.error.NoInternetException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryRepository @Inject constructor(
    private val remote: Remote,
    private val netManager: NetManager,
) : BaseRepository() {

    fun getAllCountries() = retrieveResourceAsFlow {
        if (netManager.isConnectedToInternet())
            remote.getAllCountries()
        else
            throw NoInternetException()
    }

    fun getCountry(countryName: String) = retrieveResourceAsFlow {
        if (netManager.isConnectedToInternet())
            remote.getCountry(countryName)
        else
            throw NoInternetException()
    }

}