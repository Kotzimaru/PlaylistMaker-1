package com.example.playlistmaker1.player.creator

import com.example.playlistmaker1.player.data.PlayerRepositoryImpl
import com.example.playlistmaker1.player.domain.PlayerInteractorImpl
import com.example.playlistmaker1.player.presentation.api.PlayerInteractor

object Creator {

    fun getPlayerInteractor(setOnPreparedListener: (()->Unit), setOnCompletionListener: (()->Unit)): PlayerInteractor {
        return PlayerInteractorImpl(PlayerRepositoryImpl(setOnPreparedListener, setOnCompletionListener))
    }
}