package com.neonusa.kp.data.model

data class User(
    val id: Int?,
    val nama_lengkap: String?,
    val email: String?,
    val nisn: String?,
    val no_hp: String?,
    val image: String?, // sementara Int awalnya String
    val exp: String?,
    val coin: String?,
    val motto: String?,
    )