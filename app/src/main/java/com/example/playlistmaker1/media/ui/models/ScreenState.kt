package com.example.playlistmaker1.media.ui.models

sealed interface ScreenState {

    object Empty : ScreenState

    class Content<T>(val contentList: List<T>) : ScreenState
}