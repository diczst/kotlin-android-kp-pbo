package com.neonusa.kp.data.network

import com.google.gson.GsonBuilder
import com.neonusa.kp.data.network.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    // akses api sebagai client
    private val BASE_URL = "http://192.168.43.181/pebeo/public/api/" // ini kalau pakai hotspot euler
//    const val BASE_URL = "http://10.102.14.17/pebeo/public/api/" // ini kalau pakai wifi unib


    // sebagai server : localhost:8080/

    private val client: Retrofit
        get() {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        }

    val provideApiService: ApiService
        get() = client.create(ApiService::class.java)


}