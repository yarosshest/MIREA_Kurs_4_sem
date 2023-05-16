package com.example.mirea_kurs_4_sem

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Retrofit


class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun onClickRegister(view: View) {
        val BASE_URL = "http://api.example.com/"

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build()

        val userService: Api = retrofit.create(Api::class.java)
        

        val intent = Intent(this, Register::class.java)
        startActivity(intent)
    }
}