package com.example.playlistmaker1.player.domain.api

import com.example.playlistmaker1.player.domain.PlayerState
import kotlinx.coroutines.flow.Flow

interface PlayerInteractor {
    fun startPlaying()
    fun pausePlaying()
    fun stopPlaying()
    fun getPlayerPosition(): Int
    fun getPlayerState(): PlayerState
    fun preparePlayer(url: String): Flow<PlayerState>
}