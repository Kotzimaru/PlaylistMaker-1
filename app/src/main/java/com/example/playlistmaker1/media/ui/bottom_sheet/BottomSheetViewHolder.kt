package com.example.playlistmaker1.media.ui.bottom_sheet

import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker1.R
import com.example.playlistmaker1.core.utils.setImage
import com.example.playlistmaker1.databinding.ItemViewBottomSheetBinding
import com.example.playlistmaker1.playlist_creator.domain.models.PlaylistModel

class BottomSheetViewHolder(
    private val binding: ItemViewBottomSheetBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: PlaylistModel) {
        val cornerRadius = itemView.resources.getDimensionPixelSize(R.dimen.radius_2)

        binding.tvPlaylistName.text = model.playlistName
        binding.tvTracksCount.text = itemView.resources.getQuantityString(R.plurals.tracks, model.tracksCount, model.tracksCount)

        binding.ivCover.setImage(
            url = model.coverImageUrl,
            placeholder = R.drawable.placeholder,
            cornerRadius = cornerRadius,
        )
    }
}
