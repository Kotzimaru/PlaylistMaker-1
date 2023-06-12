package com.example.playlistmaker1

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker1.player.domain.TrackDTO
import com.example.playlistmaker1.player.ui.PlayerActivity
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class TrackViewHolder(parentView: ViewGroup, var view: AppCompatActivity) : RecyclerView.ViewHolder(
    LayoutInflater.from(parentView.context).inflate(R.layout.track_list_view, parentView, false)
) {

    private val coverIcon = itemView.findViewById<ImageView>(R.id.cover_icon)
    private val trackName = itemView.findViewById<TextView>(R.id.track_name)
    private val artistName = itemView.findViewById<TextView>(R.id.artist_name)
    private val trackTime = itemView.findViewById<TextView>(R.id.track_time)


    fun bind(track: TrackDTO, history: SearchHistory) {
        val cornerRadius = itemView.resources.getDimensionPixelSize(R.dimen.radius_2)

        itemView.setOnClickListener {
            history.add(track)
            val intent = Intent(view, PlayerActivity::class.java).apply {
                putExtra("track_dto", Gson().toJson(track))
            }
            view.startActivity(intent)
        }

        trackName.text = track.trackName
        artistName.text = track.artistName
        trackTime.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis.toLong())


        Glide.with(itemView)
            .load(track.artworkUrl100)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(cornerRadius))
            .into(coverIcon)

    }
}