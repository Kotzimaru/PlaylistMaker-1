package com.example.playlistmaker1.player.ui

import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker1.R
import com.example.playlistmaker1.databinding.FragmentPlayerBinding
import com.example.playlistmaker1.player.ui.viewmodels.PlayerViewModel
import com.example.playlistmaker1.core.utils.millisConverter
import com.example.playlistmaker1.core.utils.setImage
import com.example.playlistmaker1.core.utils.viewBinding
import com.example.playlistmaker1.media.ui.bottom_sheet.BottomSheetPlaylists
import com.example.playlistmaker1.search.domain.api.TrackModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlayerFragment : Fragment(R.layout.fragment_player) {

    private val binding by viewBinding<FragmentPlayerBinding>()
    private val viewModel by viewModel<PlayerViewModel>()

    private lateinit var track: TrackModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        track = requireArguments()
            .getString(KEY_TRACK)
            ?.let { Json.decodeFromString<TrackModel>(it) } ?: TrackModel.emptyTrack

        viewModel.preparingPlayer(track.previewUrl)
        viewModel.isFavorite(track.trackId)

        viewModel.apply {
            observeFavoriteTrack().observe(viewLifecycleOwner) { isFavorite ->
                renderLikeButton(isFavorite)
            }
            observePlayStatus().observe(viewLifecycleOwner) { playingStatus ->
                renderPlayingContent(playingStatus)

            }
        }

        drawTrack(track)
        initListeners()
    }

    private fun renderLikeButton(isFavorite: Boolean) {
        val imageResource = if (isFavorite) R.drawable.button_liked
        else R.drawable.button_unliked

        binding.likeButton.setImageResource(imageResource)
    }

    private fun renderPlayingContent(status: PlayStatus) {

        val transitionDrawable = TransitionDrawable(
            arrayOf(
                AppCompatResources.getDrawable(
                    requireContext(), R.drawable.button_play_not_prepared
                ), AppCompatResources.getDrawable(requireContext(), R.drawable.button_play)
            )
        )

        when (status) {
            is PlayStatus.NotConnected, is PlayStatus.Loading -> {
                showMessage(status)
            }

            is PlayStatus.Playing -> {
                startAnimation(binding.playButton)
                renderPlayButton(status.imageResource, status.playProgress)
            }

            is PlayStatus.Paused -> {
                renderPlayButton(status.imageResource, status.playProgress)
            }

            is PlayStatus.Ready -> {
                binding.playButton.setImageDrawable(transitionDrawable)
                transitionDrawable.startTransition(TRANSITION_DURATION)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.onViewPaused()
    }

    private fun showMessage(status: PlayStatus) {

        when (status) {
            is PlayStatus.NotConnected -> {
                Toast
                    .makeText(
                        requireContext(), getString(R.string.playing_error), Toast.LENGTH_SHORT
                    )
                    .show()
            }

            else -> Toast
                .makeText(requireContext(), getString(R.string.still_loading), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun startAnimation(button: ImageButton) {
        button.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(), R.anim.scale
            )
        )
    }

    private fun renderPlayButton(imageResource: Int, currentPositionMediaPlayer: Int) {
        binding.playButton.setImageResource(imageResource)
        binding.excerptDuration.text = currentPositionMediaPlayer.millisConverter()
    }

    private fun drawTrack(trackModel: TrackModel) {

        val cornerRadius =
            requireContext().resources.getDimensionPixelSize(R.dimen.radius_8)

        binding.apply {

            cover.setImage(
                url = trackModel.artworkUrl100.replaceAfterLast("/", "512x512bb.jpg"),
                placeholder = R.drawable.placeholder,
                cornerRadius = cornerRadius,
            )
            excerptDuration.text = PlayerViewModel.START_POSITION.millisConverter()
            trackName.text = trackModel.trackName
            artistName.text = trackModel.artistName
            changeableDuration.text = trackModel.trackTimeMillis.millisConverter()
            changeableAlbum.text = trackModel.collectionName
            changeableYear.text = trackModel.releaseDate.substring(0, 4)
            changeableGenre.text = trackModel.primaryGenreName
            changeableCountry.text = trackModel.country

        }
    }

    private fun initListeners() {

        binding.apply {
            navigationToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            playButton.setOnClickListener {
                startAnimation(binding.playButton)
                viewModel.playButtonClicked(track.previewUrl)
            }

            likeButton.setOnClickListener {
                startAnimation(it as ImageButton)
                viewModel.toggleFavorite(track)
            }
            addButton.setOnClickListener { button ->
                (button as? ImageButton)?.let { startAnimation(it) }
                findNavController().navigate(
                    R.id.action_playerFragment_to_bottomSheet, BottomSheetPlaylists.createArgs(track)
                )
            }
        }
    }

    companion object {
        const val KEY_TRACK = "track"
        private const val TRANSITION_DURATION = 1000

        fun createArgs(track: TrackModel): Bundle = bundleOf(
            KEY_TRACK to Json.encodeToString(track)
        )
    }
}
