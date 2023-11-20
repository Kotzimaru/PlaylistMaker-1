package com.example.playlistmaker1.search.ui

import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker1.R
import com.example.playlistmaker1.databinding.TrackListViewBinding
import com.example.playlistmaker1.search.data.TrackDTO
import com.example.playlistmaker1.core.utils.millisConverter
import com.example.playlistmaker1.core.utils.setImage
import com.example.playlistmaker1.search.domain.api.TrackModel

class TrackViewHolder(
    private val binding: TrackListViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: TrackModel) {
        val cornerRadius = itemView.resources.getDimensionPixelSize(R.dimen.radius_2)

        binding.trackName.text = model.trackName
        binding.artistName.text = model.artistName
        binding.trackTime.text = model.trackTimeMillis.millisConverter()

        binding.cover.setImage(
            url = model.artworkUrl100,
            placeholder = R.drawable.placeholder,
            cornerRadius = cornerRadius,
        )
    }
}