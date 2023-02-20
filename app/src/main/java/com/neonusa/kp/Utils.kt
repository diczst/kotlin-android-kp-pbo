package com.neonusa.kp

import com.google.gson.Gson
import com.google.gson.internal.Primitives
import com.google.gson.reflect.TypeToken
import com.neonusa.kp.data.response.ErrorResponse
import retrofit2.Response
import java.lang.reflect.Type


fun <T> String?.toModel(classOfT: Class<T>): T? {
    if (this == null) return null
    val obj = Gson().fromJson<Any>(this, classOfT as Type)
    return Primitives.wrap(classOfT).cast(obj)!!
}

fun <T> T.toJson(): String {
    return Gson().toJson(this)
}

inline fun <reified T> Gson.fromJson(json: String): T =
    fromJson(json, object : TypeToken<T>() {}.type)

fun <T> Response<T>.getErrorBody(): ErrorResponse? {
    return try {
        this.errorBody()?.string()?.let { error ->
            Gson().fromJson<ErrorResponse>(error)
        }
    } catch (exception: Exception) {
        null
    }
}




