package com.vosaa.majoritytechassignment.util.error

import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorManager @Inject constructor(private val gson: Gson) {
    fun getAppError(errorJson: String?) {

        val errorResponse = gson.fromJson(errorJson, ErrorResponse::class.java)
        errorResponse?.let {
            it.errorCode?.let {
                throw UnknownException()
            }
        } ?: throw UnknownErrorException()
    }
}