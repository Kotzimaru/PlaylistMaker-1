package com.example.playlistmaker1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class TrackViewHolder(parentView: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parentView.context).inflate(R.layout.track_list_view, parentView, false)
) {

    val cover_icon = itemView.findViewById<ImageView>(R.id.cover_icon)
    val trackName = itemView.findViewById<TextView>(R.id.track_name)
    val artistName = itemView.findViewById<TextView>(R.id.artist_name)
    val trackTime = itemView.findViewById<TextView>(R.id.track_time)


    fun bind(track: Track) {
        val cornerRadius = itemView.resources.getDimensionPixelSize(R.dimen.radius_2)


        trackName.text = track.trackName
        artistName.text = track.artistName
        trackTime.text = track.trackTime



        Glide.with(itemView)
            .load(track.artworkUrl100)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(cornerRadius))
            .into(cover_icon)

    }
}