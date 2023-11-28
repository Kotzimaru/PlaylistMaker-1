package com.example.playlistmaker1.playlist_menu.ui.fragment

import com.example.playlistmaker1.R
import com.example.playlistmaker1.core.utils.millisConverter
import com.example.playlistmaker1.core.utils.setImage
import com.example.playlistmaker1.databinding.ItemViewTrackBinding
import com.example.playlistmaker1.search.domain.api.TrackModel
import com.example.playlistmaker1.search.ui.TrackAdapter
import com.example.playlistmaker1.search.ui.TrackViewHolder

class PlaylistMenuViewHolder(
    private val binding: ItemViewTrackBinding,
) : TrackViewHolder(binding) {

    override fun bind(
        itemList: List<TrackModel>,
        clickListener: TrackAdapter.TrackClickListener,
        longClickListener: TrackAdapter.LongClickListener?,
    ) {

        val model: TrackModel = itemList[adapterPosition]

        val cornerRadius = itemView.resources.getDimensionPixelSize(R.dimen.radius_2)

        binding.tvTrackName.text = model.trackName
        binding.tvArtistName.text = model.artistName
        binding.tvTrackTime.text = model.trackTimeMillis.millisConverter()

        binding.ivCover.setImage(
            url = model.artworkUrl60,
            placeholder = R.drawable.placeholder,
            cornerRadius = cornerRadius,
        )

        itemView.setOnClickListener { clickListener.onTrackClick(model) }

        itemView.setOnLongClickListener {
            longClickListener?.onTrackClick(model)
            return@setOnLongClickListener true
        }
    }
}
