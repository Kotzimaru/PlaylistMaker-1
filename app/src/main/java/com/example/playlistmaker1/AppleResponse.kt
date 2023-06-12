package com.example.playlistmaker1

import com.example.playlistmaker1.player.domain.TrackDTO

class AppleResponse (val searchType: String,
                         val expression: String,
                         val results: ArrayList<TrackDTO>)