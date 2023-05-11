package com.example.playlistmaker1.ui.track

import android.app.Activity
import com.example.playlistmaker1.creator.Creator
import com.example.playlistmaker1.domain.models.Track
import com.example.playlistmaker1.presentation.TrackView

class TrackActivity : Activity(), TrackView {

    // Activity specific code

    private val presenter = Creator.providePresenter(
        view = this,
        trackId = intent.extras?.getString("TRACK_KEY", "") ?: "",
    )

    override fun updateTrackLiked(liked: Boolean) {
        // update layout
    }

    override fun drawTrack(track: Track) {
        // draw track
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroyed()
    }
}