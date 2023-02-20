package com.neonusa.kp.data.model

data class User(
    val id: Int?,
    val nama: String?,
    val email: String?,
    val nisn: String?,
    val hp: String?,
    val image: String?, // sementara Int awalnya String
    val exp: String?,
)