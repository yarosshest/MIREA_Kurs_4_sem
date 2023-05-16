package com.example.mirea_kurs_4_sem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun onLoginClick(view: View) {
        val intent = Intent(this, MainAppActivity::class.java)
        startActivity(intent)
    }


    fun onClickRegister(view: View) {
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
    }
}