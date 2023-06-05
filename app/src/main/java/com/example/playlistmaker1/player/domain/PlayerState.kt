package com.example.playlistmaker1.player.domain

class PlayerState {
    companion object {
        const val TRACK_DTO = "track_dto"
        const val STATE_DEFAULT = 0
        const val STATE_PREPARED = 1
        const val STATE_PLAYING = 2
        const val STATE_PAUSED = 3
        const val RELOAD_PROGRESS = 300L
        const val CURRENT_TIME_ZERO = "00:00"
    }
}