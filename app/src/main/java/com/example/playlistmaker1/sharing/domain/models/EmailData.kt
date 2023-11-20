package com.example.playlistmaker1.sharing.domain.models

data class EmailData(
    val mail: String,
    val mailTo: String = "mailto:",
)