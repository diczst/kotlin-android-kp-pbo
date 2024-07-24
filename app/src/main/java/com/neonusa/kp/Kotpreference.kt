package com.neonusa.kp

import com.chibatching.kotpref.KotprefModel
import com.neonusa.kp.data.model.Siswa

object Kotpreference: KotprefModel() {
    var isLogin by booleanPref(false)
    var tryCount by intPref(0)
    var user by stringPref()

    fun setUser(user: Siswa?){
        this.user = user.toJson()
    }

    fun getUser(): Siswa? {
        if(user.isEmpty()) return null
        return user.toModel(Siswa::class.java)
    }
}