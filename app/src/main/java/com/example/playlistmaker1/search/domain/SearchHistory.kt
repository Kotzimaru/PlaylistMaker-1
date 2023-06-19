package com.example.playlistmaker1.search.domain

import com.example.playlistmaker1.player.domain.TrackDTO

interface SearchHistory {

    fun get(): Array<TrackDTO>

    fun add(track: TrackDTO)


}