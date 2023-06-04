package com.example.playlistmaker1.player.ui

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker1.R
import com.example.playlistmaker1.player.domain.TrackDTO
import com.example.playlistmaker1.player.domain.PlayerState
import com.example.playlistmaker1.player.presentation.PlayerPresenter
import com.example.playlistmaker1.player.ui.api.PlayerView
import com.google.gson.Gson

class PlayerActivity : AppCompatActivity(), PlayerView {

    private lateinit var backButton: ImageView
    private lateinit var coverImage: ImageView
    private lateinit var trackName: TextView
    private lateinit var artistName: TextView
    private lateinit var duration: TextView
    private lateinit var excerptDuration: TextView
    private lateinit var albumName: TextView
    private lateinit var year: TextView
    private lateinit var genre: TextView
    private lateinit var country: TextView
    private lateinit var addButton: ImageButton
    private lateinit var playButton: ImageButton
    private lateinit var likeButton: ImageButton


    private lateinit var track: TrackDTO
    private lateinit var presenter: PlayerPresenter
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        initValues()
        initViews()
        fillViews(track)
        presenter.preparePlayer(track)
        presenter.onCompletionListener()

        playButton.setOnClickListener {
            presenter.onClickPlayAndPause()
        }

        backButton.setOnClickListener {
            presenter.backButtonClicked()
        }
    }

    private fun initValues() {
        presenter = PlayerPresenter(this)
        track = gson.fromJson(
            intent.getStringExtra(PlayerState.TRACK_DTO),
            TrackDTO::class.java
        )
    }

    private fun initViews() {
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

    private fun fillViews(track: TrackDTO) {
        trackName.text = track.trackName
        artistName.text = track.artistName
        duration.text = presenter.millisFormat(track)
        albumName.text = track.collectionName
        year.text = presenter.fixReleaseDate(track.releaseDate)
        genre.text = track.primaryGenreName
        country.text = track.country
        Glide.with(applicationContext)
            .load(track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
            .centerCrop()
            .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.radius_8)))
            .placeholder(R.drawable.placeholder)
            .into(coverImage)
    }

    override fun onPause() {
        super.onPause()
        presenter.pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearPlayer()
    }

    override fun setImage(image: Int) {
        playButton.setImageResource(image)
    }

    override fun setCurrentTime(time: String) {
        excerptDuration.text = time
    }

    override fun goBack() {
        finish()
    }
}