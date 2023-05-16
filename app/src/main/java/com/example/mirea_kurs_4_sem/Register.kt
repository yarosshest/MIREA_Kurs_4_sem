package com.example.mirea_kurs_4_sem

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun onClickRegister(view: View) {
        val BASE_URL = "http://api.example.com/"

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val userService: Api = retrofit.create(Api::class.java)
            val response = userService.register("login", "password").execute()

            if (response) {
                Toast.makeText(this@Register, "User registered", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@Register, "Error", Toast.LENGTH_LONG).show()
            }

        }




        val intent = Intent(this, Register::class.java)
        startActivity(intent)
    }
}