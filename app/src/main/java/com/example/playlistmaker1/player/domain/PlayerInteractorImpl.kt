package com.example.playlistmaker1.player.domain

import com.example.playlistmaker1.player.data.TrackDTO
import com.example.playlistmaker1.player.domain.api.PlayerInteractor
import com.example.playlistmaker1.player.domain.api.PlayerRepository
import kotlinx.coroutines.flow.Flow


class PlayerInteractorImpl(
    private val player: PlayerRepository
) : PlayerInteractor {


    override fun startPlaying() {
        player.startPlayer()
    }

    override fun pausePlaying() {
        player.pausePlayer()
    }

    override fun stopPlaying() {
        player.stopPlayer()
    }

    override fun getPlayerPosition(): Int {
        return player.getCurrentPosition()
    }

    override fun getPlayerState(): PlayerState {
        return player.playerState
    }

    override fun preparePlayer(url: String): Flow<PlayerState> {
        return player.preparePlayer(url)
    }

}