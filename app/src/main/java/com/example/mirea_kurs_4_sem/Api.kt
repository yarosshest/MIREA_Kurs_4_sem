package com.example.mirea_kurs_4_sem

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface Api {

    @POST("register")
    fun register(
        @Query("log") login: String,
        @Query("password") password: String,
        ): Int

}