package com.neonusa.kp.data.response

data class BaseSingleResponse<T>(
    val code: Int? = null,
    val message: String? = null,
    val data: T? = null
)