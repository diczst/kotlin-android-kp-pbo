package com.neonusa.kp.data.request

data class AddLevelMateriUserRequest(
    val id: Int,
    val level_materi: Int? = null,
    val level_tantangan: Int? = null
)