package com.vosaa.majoritytechassignment.data.remote

import com.vosaa.majoritytechassignment.util.error.ErrorManager
import retrofit2.Response
import timber.log.Timber

open class BaseRemote(private val errorManager: ErrorManager) {

    protected suspend fun <T> parseResult(networkCall: suspend () -> Response<T>): T? {
        val response = networkCall.invoke()
        if (!response.isSuccessful) {
            val errorBody = response.errorBody()?.string()
            errorManager.getAppError(errorBody)
        }
        return response.body()
    }

}