package com.example.playlistmaker1

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.NonDisposableHandle.parent

class TrackAdapter : RecyclerView.Adapter<TrackViewHolder>() {

    var track = ArrayList<Track>()
    var history: SearchHistory? = null
    fun deleteList(track: ArrayList<Track>, adapter: TrackAdapter){
        track.clear()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TrackViewHolder(parent)

    override fun getItemCount(): Int = track.size

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(track[position], history!!)

    }

}


