package com.example.playlistmaker1.media.ui.child

import androidx.fragment.app.Fragment
import com.example.playlistmaker1.R
import com.example.playlistmaker1.core.utils.viewBinding
import com.example.playlistmaker1.databinding.FragmentPlaylistsBinding
import com.example.playlistmaker1.media.ui.viewmodels.PlaylistsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistsFragment : Fragment(R.layout.fragment_playlists) {


    private val binding by viewBinding<FragmentPlaylistsBinding>()
    private val viewModel by viewModel<PlaylistsViewModel>()

    companion object {
        fun newInstance() = PlaylistsFragment()
    }

}