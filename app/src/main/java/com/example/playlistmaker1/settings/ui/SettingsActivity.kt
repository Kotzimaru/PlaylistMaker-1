package com.example.playlistmaker1.settings.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker1.R
import com.example.playlistmaker1.settings.data.App
import com.google.android.material.switchmaterial.SwitchMaterial


const val PRACTICUM_EXAMPLE_PREFERENCES = "practicum_example_preferences"
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

        val themeSwitcher = findViewById<SwitchMaterial>(R.id.themeSwitcher)

        themeSwitcher.setOnCheckedChangeListener { switcher, checked ->
            (applicationContext as App).switchTheme(checked)
        }


        findViewById<ImageView>(R.id.offer).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_offer)))
            startActivity(intent)
        }
        themeSwitcher.isChecked = (applicationContext as App).darkTheme

    }
}