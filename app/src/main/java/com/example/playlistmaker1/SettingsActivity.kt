package com.example.playlistmaker1

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class SettingsActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        findViewById<ImageView>(R.id.arrow_back).setOnClickListener {
            finish()
        }

        findViewById<ImageView>(R.id.support).setOnClickListener{

            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(R.string.mail))
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.theme_message))
            intent.putExtra(Intent.EXTRA_TEXT,getString(R.string.body_message))
            startActivity(Intent.createChooser(intent,"Send Email"))
        }


        findViewById<ImageView>(R.id.offer).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_offer)))
            startActivity(intent)
        }

    }
}