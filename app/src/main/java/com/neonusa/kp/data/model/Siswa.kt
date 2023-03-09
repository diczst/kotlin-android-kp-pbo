package com.neonusa.kp.data.model

data class Siswa(
    val id: Int?,
    val nama_lengkap: String?,
    val email: String?,
    val nisn: String?,
    val no_hp: String?,
    val image: String?, // sementara Int awalnya String
    var exp: Int?,
    val coin: Int?,
    val motto: String?,
    )