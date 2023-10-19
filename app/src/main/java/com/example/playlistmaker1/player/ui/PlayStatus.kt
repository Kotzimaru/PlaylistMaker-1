package com.example.playlistmaker1.player.ui

import com.example.playlistmaker1.R
import com.example.playlistmaker1.player.ui.viewmodels.PlayerViewModel


sealed class PlayStatus(val imageResource: Int, val playProgress: Int) {
    
    class Loading : PlayStatus(
        imageResource = R.drawable.button_play_not_prepared,
        playProgress = PlayerViewModel.START_POSITION
    )
    
    class Ready :
        PlayStatus(imageResource = R.drawable.button_play, playProgress = PlayerViewModel.START_POSITION)
    
    class Playing(playProgress: Int) :
        PlayStatus(imageResource = R.drawable.button_pause, playProgress = playProgress)
    
    class Paused(playProgress: Int) :
        PlayStatus(imageResource = R.drawable.button_play, playProgress = playProgress)
    
    class NotConnected(playProgress: Int) :
        PlayStatus(imageResource = R.drawable.button_play, playProgress = playProgress)
}
