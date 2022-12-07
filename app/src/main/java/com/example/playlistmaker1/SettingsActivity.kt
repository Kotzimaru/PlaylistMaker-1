package com.example.playlistmaker1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SettingsActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)



        val arrowBack = findViewById<Button>(R.id.arrow_back)


        arrowBack.setOnClickListener {
            val mainDisplayIntent = Intent(this, MainActivity::class.java)
            startActivity(mainDisplayIntent)}
    }
}