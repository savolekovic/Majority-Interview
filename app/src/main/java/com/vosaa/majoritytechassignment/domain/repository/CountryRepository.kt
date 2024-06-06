package com.vosaa.majoritytechassignment.domain.repository

import androidx.datastore.core.DataStore
import com.vosaa.majoritytechassignment.data.remote.BaseRepository
import com.vosaa.majoritytechassignment.domain.models.ResponseCountry
import com.vosaa.majoritytechassignment.domain.remote.Remote
import com.vosaa.majoritytechassignment.util.NetManager
import com.vosaa.majoritytechassignment.util.error.NoInternetException
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryRepository @Inject constructor(
    private val remote: Remote,
    private val netManager: NetManager,
    private val dataStore: DataStore<ResponseCountry>,
) : BaseRepository() {

    private suspend fun isDataStoreEmpty(): Boolean {
        return dataStore.data.first().countries.isEmpty()
    }

    fun getAllCountries() = retrieveResourceAsFlow {
        if (netManager.isConnectedToInternet()) {
            if (isDataStoreEmpty()) {
                val response = remote.getAllCountries()
                dataStore.updateData {
                    it.copy(
                        countries = response!!
                    )
                }.countries
            } else
                dataStore.data.first().countries

        } else
            throw NoInternetException()
    }

    fun getCountry(countryName: String) = retrieveResourceAsFlow {
        if (netManager.isConnectedToInternet())
            remote.getCountry(countryName)
        else
            throw NoInternetException()
    }

}