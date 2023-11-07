package com.example.playlistmaker1.media.ui.child

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker1.R
import com.example.playlistmaker1.core.ui.HostActivity
import com.example.playlistmaker1.core.utils.debounce
import com.example.playlistmaker1.core.utils.viewBinding
import com.example.playlistmaker1.databinding.FragmentFavoriteTracksBinding
import com.example.playlistmaker1.media.ui.models.FavoriteState
import com.example.playlistmaker1.media.ui.viewmodels.FavoritesViewModel
import com.example.playlistmaker1.player.ui.PlayerFragment
import com.example.playlistmaker1.search.domain.api.TrackModel
import com.example.playlistmaker1.search.ui.TrackAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteFragment: Fragment(R.layout.fragment_favorite_tracks)  {

    private lateinit var onClickDebounce: (TrackModel) -> Unit
    private val binding by viewBinding<FragmentFavoriteTracksBinding>()
    private val viewModel by viewModel<FavoritesViewModel>()
    private var trackAdapter: TrackAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickDebounce = debounce(delayMillis = CLICK_DEBOUNCE_DELAY,
            coroutineScope = viewLifecycleOwner.lifecycleScope,
            useLastParam = false,
            action = { track ->
                findNavController().navigate(
                    R.id.action_mediaFragment_to_playerFragment,
                    PlayerFragment.createArgs(track)
                )
            })

        viewModel
            .observeContentState()
            .observe(viewLifecycleOwner) { contentState ->
                render(contentState)
            }

        initAdapter()
    }

    private fun initAdapter() {
        trackAdapter = TrackAdapter { track ->
            (activity as HostActivity).animateBottomNavigationView()
            onClickDebounce(track)
        }
        binding.tracksList.adapter = trackAdapter
    }

    private fun render(state: FavoriteState) {
        when (state) {
            is FavoriteState.SelectedTracks -> {
                showContent(state.trackList)
            }

            FavoriteState.Empty -> showMessage()
        }
    }

    private fun showMessage() {
        binding.apply {
            placeholder.visibility = View.VISIBLE
            tracksList.visibility = View.GONE
        }
    }

    private fun showContent(list: List<TrackModel>) {
        binding.apply {
            placeholder.visibility = View.GONE
            tracksList.visibility = View.VISIBLE
        }
        trackAdapter?.apply {
            trackList.clear()
            trackList.addAll(list)
            notifyDataSetChanged()
        }
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 300L
    }
}