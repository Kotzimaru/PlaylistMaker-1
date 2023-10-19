package com.example.playlistmaker1.player.ui

import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker1.R
import com.example.playlistmaker1.databinding.FragmentPlayerBinding
import com.example.playlistmaker1.player.data.TrackDTO
import com.example.playlistmaker1.player.ui.viewmodels.PlayerViewModel
import com.example.playlistmaker1.core.utils.millisConverter
import com.example.playlistmaker1.core.utils.setImage
import com.example.playlistmaker1.core.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlayerFragment : Fragment(R.layout.fragment_player) {

    private val binding by viewBinding<FragmentPlayerBinding>()
    private val viewModel by viewModel<PlayerViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val transitionDrawable = TransitionDrawable(arrayOf(
            AppCompatResources.getDrawable(requireContext(), R.drawable.button_play_not_prepared),
            AppCompatResources.getDrawable(requireContext(), R.drawable.button_play)
        ))

        val trackModel = viewModel.getTrack()

        viewModel.apply {
            observePlayStatus().observe(viewLifecycleOwner) { playingStatus ->
                when (playingStatus) {
                    is PlayStatus.NotConnected, is PlayStatus.Loading -> {
                        showMessage(playingStatus)
                    }
                    is PlayStatus.Playing -> {
                        startAnimation()
                        renderPlayer(playingStatus.imageResource, playingStatus.playProgress)
                    }
                    is PlayStatus.Paused -> {
                        renderPlayer(playingStatus.imageResource, playingStatus.playProgress)
                    }
                    is PlayStatus.Ready -> {
                        binding.playButton.setImageDrawable(transitionDrawable)
                        transitionDrawable.startTransition(TRANSITION_DURATION)
                    }
                }
            }
        }
        drawTrack(trackModel, PlayerViewModel.START_POSITION)
        initListeners()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onViewPaused()
    }

    private fun showMessage(status: PlayStatus) {

        when(status) {
            is PlayStatus.NotConnected -> { Toast
                .makeText(context, getString(R.string.playing_error), Toast.LENGTH_SHORT)
                .show() }
            else -> Toast
                .makeText(context, getString(R.string.still_loading), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun startAnimation() {
        binding.playButton.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(), R.anim.scale
            )
        )
    }

    private fun renderPlayer(imageResource: Int, currentPositionMediaPlayer: Int) {
        binding.playButton.setImageResource(imageResource)
        binding.excerptDuration.text = currentPositionMediaPlayer.millisConverter()
    }

    private fun drawTrack(trackModel: TrackDTO, startPosition: Int) {

        val cornerRadius = this.resources.getDimensionPixelSize(R.dimen.radius_8)

        binding.apply {

            cover.setImage(
                context = requireContext(),
                url = trackModel.artworkUrl100.replaceAfterLast("/", "512x512bb.jpg"),
                placeholder = R.drawable.placeholder,
                cornerRadius = cornerRadius,
            )
            excerptDuration.text = startPosition.millisConverter()
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
                startAnimation()
                viewModel.playButtonClicked()
            }
        }
    }

    companion object {
        private const val TRANSITION_DURATION = 1000
    }
}
