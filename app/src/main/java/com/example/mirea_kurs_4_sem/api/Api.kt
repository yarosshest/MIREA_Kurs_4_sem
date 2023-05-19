package com.example.mirea_kurs_4_sem.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.Dictionary


interface Api {

    @GET("register")
    fun register(
        @Query("log") login: String,
        @Query("password") password: String,
        ): Call<Map<String,Int>>

    @GET("login")
    fun login(
        @Query("log") login: String,
        @Query("password") password: String,
    ): Call<Map<String,Int>>

}