package com.example.playlistmaker1.settings.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.playlistmaker1.R
import com.example.playlistmaker1.core.utils.viewBinding
import com.example.playlistmaker1.databinding.FragmentSettingsBinding
import com.example.playlistmaker1.settings.ui.viewmodels.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment(R.layout.fragment_settings) {


    private val _binding by viewBinding<FragmentSettingsBinding>()
    private val viewModel by viewModel<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel
            .observeThemeSwitcherState()
            .observe(viewLifecycleOwner) { isChecked ->
                _binding.themeSwitcher.isChecked = isChecked
            }


        _binding.apply {
            themeSwitcher.setOnCheckedChangeListener { _, isChecked ->
                viewModel.onThemeSwitcherClicked(isChecked)
            }


            _binding.share.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.site))
                startActivity(intent)
            }

            _binding.offer?.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(getString(R.string.url_offer))
                startActivity(intent)
            }

            _binding.support.setOnClickListener {

                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:")
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(R.string.mail))
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.theme_message))
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.body_message))
                startActivity(Intent.createChooser(intent, "Send Email"))
            }

        }
    }
}

