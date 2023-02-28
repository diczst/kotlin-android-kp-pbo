package com.neonusa.kp.data.model

data class Soal(
    val id: Int?,
    val tantangan_id: Int?,
    val soal: String?,
    val image: String?,
    val option_a: String?,
    val option_b: String?,
    val option_c: String?,
    val option_d: String?,
    val jawaban: Int,
    val pembahasan: String?
)
