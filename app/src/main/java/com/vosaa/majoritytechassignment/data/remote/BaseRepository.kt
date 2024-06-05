package com.vosaa.majoritytechassignment.data.remote

import kotlinx.coroutines.flow.flow

open class BaseRepository {
    protected fun <T> retrieveResourceAsFlow(response: suspend () -> T) = flow {
        val result = response.invoke()
        emit(result)
    }

}