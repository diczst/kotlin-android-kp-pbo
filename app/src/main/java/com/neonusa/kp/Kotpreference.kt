package com.neonusa.kp

import com.chibatching.kotpref.KotprefModel
import com.neonusa.kp.data.model.Siswa

object Kotpreference: KotprefModel() {
    var isLogin by booleanPref(false)
    var user by stringPref()
    var level by intPref(1)

    fun setUser(user: Siswa?){
        this.user = user.toJson()
    }

    fun getUser(): Siswa? {
        if(user.isEmpty()) return null
        return user.toModel(Siswa::class.java)
    }
}