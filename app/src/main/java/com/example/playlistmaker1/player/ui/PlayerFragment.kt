package com.example.playlistmaker1.player.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker1.R
import com.example.playlistmaker1.databinding.FragmentPlayerBinding
import com.example.playlistmaker1.player.data.TrackDTO
import com.example.playlistmaker1.player.domain.StateMusicPlayer
import com.example.playlistmaker1.player.ui.viewmodels.PlayerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerFragment : Fragment() {

    private lateinit var backButton: LinearLayout
    private lateinit var coverImage: ImageView
    private lateinit var trackName: TextView
    private lateinit var artistName: TextView
    private lateinit var duration: TextView
    private lateinit var albumName: TextView
    private lateinit var year: TextView
    private lateinit var genre: TextView
    private lateinit var country: TextView
    private lateinit var addButton: ImageButton
    private lateinit var playButton: ImageButton
    private lateinit var likeButton: ImageButton
    private lateinit var track: TrackDTO
    private lateinit var excerptDuration: TextView
    private lateinit var binding: FragmentPlayerBinding

    companion object {
        const val TRACK = "track"

        fun createArgs(track: String?): Bundle {
            return bundleOf(TRACK to track)
        }
    }

    private val viewModel: PlayerViewModel by viewModel {
        parametersOf(requireArguments().getString(TRACK))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton = binding.back
        coverImage = binding.cover
        trackName = binding.trackName
        artistName = binding.artistName
        duration = binding.changeableDuration
        albumName = binding.changeableAlbum
        year = binding.changeableYear
        genre = binding.changeableGenre
        country = binding.changeableCountry
        addButton = binding.addButton
        playButton = binding.playButton
        likeButton = binding.likeButton
        excerptDuration = binding.excerptDuration

        preparePlayer()

        fun playbackControl() = viewModel.playbackControl()

        playButton.setOnClickListener { playbackControl() }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        //получение трека
        viewModel.getTrackData().observe(viewLifecycleOwner) {
            track = it

            trackName.text = track.trackName
            artistName.text = track.artistName
            duration.text =
                SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
            albumName.text = track.collectionName
            year.text = viewModel.fixReleaseDate(track.releaseDate)
            genre.text = track.primaryGenreName
            country.text = track.country

            Glide.with(requireContext())
                .load(track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
                .centerCrop()
                .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.radius_8)))
                .placeholder(R.drawable.placeholder)
                .into(coverImage)
        }

        viewModel.getTimerTextData().observe(viewLifecycleOwner) {
            excerptDuration.text = it
        }
        //получение состояния
        viewModel.getStateData().observe(viewLifecycleOwner) { it ->

            if (it == StateMusicPlayer.PLAYING) {
                playButton.setImageResource(R.drawable.button_pause)
            } else {
                playButton.setImageResource(R.drawable.button_play)
            }
        }

    }
    private fun preparePlayer() {
        viewModel.preparePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pausePlayer()
    }
}