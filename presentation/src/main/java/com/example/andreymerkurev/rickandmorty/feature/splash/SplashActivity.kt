package com.example.andreymerkurev.rickandmorty.feature.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.andreymerkurev.rickandmorty.feature.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(2000L)
        doFirstRunCheckup()
    }

    private fun doFirstRunCheckup() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }
}