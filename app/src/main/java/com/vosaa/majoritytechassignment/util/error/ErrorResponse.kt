package com.vosaa.majoritytechassignment.util.error

data class ErrorResponse(
    val title: String?,
    val status: Int?,
    val traceId: String?,
    val requestPath: String?,
    val errorCode: String?,
)