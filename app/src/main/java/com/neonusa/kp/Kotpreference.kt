package com.neonusa.kp

import com.chibatching.kotpref.KotprefModel
import com.google.gson.Gson
import com.google.gson.internal.Primitives
import com.neonusa.kp.data.model.User
import java.lang.reflect.Type

object Kotpreference: KotprefModel() {
    var isLogin by booleanPref(false)
    var user by stringPref()

    fun setUser(user: User?){
        this.user = user.toJson()
    }

    fun getUser(): User? {
        if(user.isEmpty()) return null
        return user.toModel(User::class.java)
    }
}