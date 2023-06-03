package com.example.playlistmaker1.player.creator

import com.example.playlistmaker1.player.data.PlayerRepositoryImpl
import com.example.playlistmaker1.player.domain.PlayerInteractor
import com.example.playlistmaker1.player.presentation.PlayerPresenter

object Creator {

    fun getPlayerInteractor(playerPresenter: PlayerPresenter): PlayerInteractor {
        return PlayerInteractor(PlayerRepositoryImpl(playerPresenter))
    }
}