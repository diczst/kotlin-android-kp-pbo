package com.neonusa.kp.data.request

data class UpdateUserRequest(
    val id: Int,
    val nama_lengkap: String? = null,
    val motto: String? = null
)