package com.example.playlistmaker1.player.ui

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker1.R
import com.example.playlistmaker1.player.data.TrackDTO
import com.example.playlistmaker1.player.domain.StateMusicPlayer
import com.example.playlistmaker1.player.ui.viewmodels.PlayerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*

class PlayerActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
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
    private val toast = {
        Toast.makeText(this, R.string.warning, Toast.LENGTH_SHORT).show()
    }

    private val viewModel: PlayerViewModel by viewModel{
        parametersOf(intent.getStringExtra("track"), toast)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        initViews()


        /*viewModel = ViewModelProvider(
            this,
            PlayerViewModelFactory(intent.getStringExtra("track"))
        )[PlayerViewModel::class.java]*/

        viewModel.getTrackData().observe(this) {
            track = it

            trackName.text = track.trackName
            artistName.text = track.artistName
            duration.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
            albumName.text = track.collectionName
            year.text = viewModel.fixReleaseDate(track.releaseDate)
            genre.text = track.primaryGenreName
            country.text = track.country

            Glide.with(applicationContext)
                .load(track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
                .centerCrop()
                .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.radius_8)))
                .placeholder(R.drawable.placeholder)
                .into(coverImage)
        }

        viewModel.getTimerTextData().observe(this) {
            excerptDuration.text = it
        }

        playButton.setOnClickListener { playbackControl() }

        backButton.setOnClickListener { finish() }
        preparePlayer()





        viewModel.getStateData().observe(this) { it ->

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

    fun initViews() {
        backButton = findViewById(R.id.arrow_back)
        coverImage = findViewById(R.id.cover)
        trackName = findViewById(R.id.track_name)
        artistName = findViewById(R.id.artist_name)
        duration = findViewById(R.id.changeable_duration)
        albumName = findViewById(R.id.changeable_album)
        year = findViewById(R.id.changeable_year)
        genre = findViewById(R.id.changeable_genre)
        country = findViewById(R.id.changeable_country)
        addButton = findViewById(R.id.add_button)
        playButton = findViewById(R.id.play_button)
        likeButton = findViewById(R.id.like_button)
        excerptDuration = findViewById(R.id.excerpt_duration)
    }

   private fun playbackControl() = viewModel.playbackControl()



override fun onDestroy() {
    super.onDestroy()
    viewModel.onDestroy()
}

override fun onPause() {
    super.onPause()
    viewModel.pausePlayer()
}
}