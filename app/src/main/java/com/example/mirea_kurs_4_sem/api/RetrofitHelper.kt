package com.example.mirea_kurs_4_sem.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHelper {

    private const val BASE_URL = "http://172.17.32.1:8031/"
    private val client: OkHttpClient = OkHttpClient.Builder()
        .cookieJar(MyCookieJar())
        .build()

    fun getInstance(): Retrofit {


        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}