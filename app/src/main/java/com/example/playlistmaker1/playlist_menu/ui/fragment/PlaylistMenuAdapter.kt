package com.example.playlistmaker1.playlist_menu.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.playlistmaker1.databinding.ItemViewTrackBinding
import com.example.playlistmaker1.search.ui.TrackAdapter
import com.example.playlistmaker1.search.ui.TrackViewHolder

class PlaylistMenuAdapter(
    private val clickListener: TrackClickListener,
    private val longClickListener: LongClickListener,
) : TrackAdapter(clickListener, longClickListener) {
    
    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(trackList, clickListener, longClickListener)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistMenuViewHolder {
        return PlaylistMenuViewHolder(
            ItemViewTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}

