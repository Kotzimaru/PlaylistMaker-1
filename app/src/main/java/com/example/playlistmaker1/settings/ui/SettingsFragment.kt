package com.example.playlistmaker1.settings.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.playlistmaker1.R
import com.example.playlistmaker1.databinding.FragmentSettingsBinding
import com.example.playlistmaker1.settings.ui.viewmodels.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {

    private var _binding : FragmentSettingsBinding? = null
    private val binding get() = _binding
    private val viemModel: SettingsViewModel by viewModel()
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)

        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding!!.share.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.site))
            startActivity(intent)
        }

        _binding?.offer?.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(getString(R.string.url_offer))
            startActivity(intent)
        }

        _binding!!.support.setOnClickListener {

            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(R.string.mail))
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.theme_message))
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.body_message))
            startActivity(Intent.createChooser(intent, "Send Email"))
        }

        val themeSwitcher = _binding!!.themeSwitcher
        themeSwitcher.isChecked = resources.configuration.isNightModeActive

        themeSwitcher.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        _binding!!.themeSwitcher.isChecked = resources.configuration.isNightModeActive

        _binding!!.themeSwitcher.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            viemModel.saveDarkThemeValue(isChecked)
        }

        /*val themeSwitcher = _binding!!.themeSwitcher

        themeSwitcher.setOnCheckedChangeListener { switcher, checked ->
            (requireActivity().applicationContext as App).switchTheme(checked)
        }

        themeSwitcher.isChecked = (requireActivity().applicationContext as App).darkTheme*/


        /*val themeSwitcher = findViewById<SwitchMaterial>(R.id.themeSwitcher)

        themeSwitcher.setOnCheckedChangeListener { switcher, checked ->
            (applicationContext as App).switchTheme(checked)
        }
        themeSwitcher.isChecked = (applicationContext as App).darkTheme*/

    }
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onResume() {
        super.onResume()
        _binding!!.themeSwitcher.isChecked = resources.configuration.isNightModeActive
    }

    /*override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }*/

}