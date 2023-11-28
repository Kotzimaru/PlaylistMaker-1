package com.example.playlistmaker1.settings.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.playlistmaker1.R
import com.example.playlistmaker1.core.utils.viewBinding
import com.example.playlistmaker1.databinding.FragmentSettingsBinding
import com.example.playlistmaker1.settings.ui.viewmodels.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment(R.layout.fragment_settings) {


    private val binding by viewBinding<FragmentSettingsBinding>()
    private val viewModel by viewModel<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel
            .themeSwitcherState
            .observe(viewLifecycleOwner) { isChecked ->
                binding.themeSwitcher.isChecked = isChecked
            }

        binding.apply {
            themeSwitcher.setOnCheckedChangeListener { _, isChecked ->
                viewModel.onThemeSwitcherClicked(isChecked)
            }

            share.setOnClickListener {
                viewModel.onShareAppClicked()
            }

            support.setOnClickListener {
                viewModel.onWriteSupportClicked()
            }

            offer.setOnClickListener {
                viewModel.termsOfUseClicked()
            }
        }
    }
}

