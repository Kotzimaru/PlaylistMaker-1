package com.example.playlistmaker1.search.data.network

import com.example.playlistmaker1.player.data.TrackDTO

class SearchResponse(val results: List<TrackDTO>) : Response()