package com.example.playlistmaker1.media.ui.child.playlists

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker1.R
import com.example.playlistmaker1.core.utils.viewBinding
import com.example.playlistmaker1.databinding.FragmentPlaylistsBinding
import com.example.playlistmaker1.media.ui.models.PlaylistsScreenState
import com.example.playlistmaker1.media.ui.viewmodels.PlaylistsViewModel
import com.example.playlistmaker1.playlist_creator.domain.models.PlaylistModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistsFragment : Fragment(R.layout.fragment_playlists) {

    private val binding by viewBinding<FragmentPlaylistsBinding>()
    private val viewModel by viewModel<PlaylistsViewModel>()

    private lateinit var playlistsAdapter: PlaylistsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.contentFlow.collect { screenState ->
                render(screenState)
            }
        }

        binding.newPlaylistBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_mediaFragment_to_newPlaylistFragment
            )
        }
    }

    private fun render(state: PlaylistsScreenState) {
        when (state) {
            is PlaylistsScreenState.Content -> showContent(state.playlists)
            PlaylistsScreenState.Empty -> showPlaceholder()
        }
    }

    private fun showPlaceholder() {
        binding.apply {
            placeholdersGroup.visibility = View.VISIBLE
            playlists.visibility = View.GONE
        }

    }

    private fun showContent(content: List<PlaylistModel>) {

        binding.apply {
            placeholdersGroup.visibility = View.GONE
            playlists.visibility = View.VISIBLE
        }


        playlistsAdapter.apply {
            playlists.clear()
            playlists.addAll(content)
            notifyDataSetChanged()
        }
    }

    private fun initAdapter() {
        playlistsAdapter = PlaylistsAdapter { playlist ->

            Toast
                .makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT)
                .show()

        }

        binding.playlists.adapter = playlistsAdapter
        binding.playlists.addItemDecoration(PlaylistsOffsetItemDecoration(requireContext()))
    }

    companion object {
        fun newInstance() = PlaylistsFragment()
    }
}
