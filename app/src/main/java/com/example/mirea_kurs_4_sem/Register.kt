package com.example.mirea_kurs_4_sem

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Dispatchers
import android.util.Log
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun onClickRegister(view: View) {


        val BASE_URL = "http://api.example.com/"

        val Api = RetrofitHelper.getInstance().create(Api::class.java)

        GlobalScope.launch {
            val result = Api.register(login = )
            if (result != null)
            // Checking the results
                Log.d("ayush: ", result.body().toString())
        }
    }
}