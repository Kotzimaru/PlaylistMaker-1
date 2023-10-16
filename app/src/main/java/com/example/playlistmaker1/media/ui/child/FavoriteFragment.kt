package com.example.playlistmaker1.media.ui.child

import androidx.fragment.app.Fragment
import com.example.playlistmaker1.R
import com.example.playlistmaker1.core.utils.viewBinding
import com.example.playlistmaker1.databinding.FragmentFavoriteBinding
import com.example.playlistmaker1.media.ui.viewmodels.FavoritesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteFragment: Fragment(R.layout.fragment_favorite)  {
    private val binding by viewBinding<FragmentFavoriteBinding>()
    private val viewModel by viewModel<FavoritesViewModel>()

    companion object {
        fun newInstance() = FavoriteFragment()
    }

}