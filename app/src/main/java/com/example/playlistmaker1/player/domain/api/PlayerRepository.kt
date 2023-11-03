package com.example.playlistmaker1.player.domain.api

import com.example.playlistmaker1.player.domain.PlayerState
import kotlinx.coroutines.flow.Flow


interface PlayerRepository {

    var playerState: PlayerState
    fun getCurrentPosition(): Int
    fun startPlayer()
    fun pausePlayer()
    fun stopPlayer()
    fun preparePlayer(url: String): Flow<PlayerState>
}
