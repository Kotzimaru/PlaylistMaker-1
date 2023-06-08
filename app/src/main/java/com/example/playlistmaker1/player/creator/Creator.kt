package com.example.playlistmaker1.player.creator

import com.example.playlistmaker1.player.data.PlayerRepositoryImpl
import com.example.playlistmaker1.player.domain.PlayerInteractorImpl
import com.example.playlistmaker1.player.presentation.api.PlayerInteractor

object Creator {

    fun getPlayerInteractor(): PlayerInteractor {
        return PlayerInteractorImpl()
    }

    fun getRepositoryImpl(): PlayerRepositoryImpl {
        return PlayerRepositoryImpl()
    }
}