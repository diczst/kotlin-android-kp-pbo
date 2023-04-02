package com.neonusa.kp.data.request

data class UpdateCoinRequest(
    val id: Int,
    val coin: Int? = null,
)