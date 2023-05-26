package com.example.playlistmaker1

import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker1.player.domain.TrackDTO

class TrackAdapter() : RecyclerView.Adapter<TrackViewHolder>() {

    lateinit var view: AppCompatActivity
    var track = ArrayList<TrackDTO>()
    var history: SearchHistory? = null
    fun deleteList(track: ArrayList<TrackDTO>, adapter: TrackAdapter){
        track.clear()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TrackViewHolder(parent, view)

    override fun getItemCount(): Int = track.size

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(track[position], history!!)

    }


}


