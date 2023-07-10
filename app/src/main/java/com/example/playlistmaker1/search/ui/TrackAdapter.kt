package com.example.playlistmaker1.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker1.R
import com.example.playlistmaker1.player.data.TrackDTO

class TrackAdapter() : RecyclerView.Adapter<TrackViewHolder>() {

    var track = ArrayList<TrackDTO>()
    var itemClickListener: ((Int, TrackDTO) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.track_list_view, parent, false)
        return TrackViewHolder(view)
    }

    override fun getItemCount(): Int = track.size

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(track[position])
        holder.itemView.setOnClickListener { itemClickListener?.invoke(position, track[position]) }

    }


}


