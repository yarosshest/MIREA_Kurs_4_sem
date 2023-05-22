package com.example.mirea_kurs_4_sem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_app)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController


        val bnv = findViewById<BottomNavigationView >(R.id.bottomNavigationView)

        bnv.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.search -> {
                    navController.navigate(R.id.findFragment)
                    return@setOnItemSelectedListener true
                }


                else -> {false}
            }
        }
    }
}