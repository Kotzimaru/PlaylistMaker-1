package com.example.playlistmaker1.player.domain

class PlayerState {
    companion object {
        const val TRACK_DTO = "track_dto"
        const val KEY_LIST_TRACKS = "tracks_history"
        const val RELOAD_PROGRESS = 300L
        const val CURRENT_TIME_ZERO = "00:00"
        const val CLICK_DEBOUNCE_DELAY = 1000L
        const val SEARCH_DEBOUNCE_DELAY = 2000L
        const val FALSE = "false"
    }
}