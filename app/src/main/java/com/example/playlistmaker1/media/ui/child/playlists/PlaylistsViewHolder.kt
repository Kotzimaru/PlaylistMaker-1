package com.example.playlistmaker1.media.ui.child.playlists

import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker1.R
import com.example.playlistmaker1.core.utils.setImage
import com.example.playlistmaker1.databinding.ItemViewPlaylistBinding
import com.example.playlistmaker1.playlist_creator.domain.models.PlaylistModel

class PlaylistsViewHolder(
    private val binding: ItemViewPlaylistBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: PlaylistModel) {
        val cornerRadius = itemView.resources.getDimensionPixelSize(R.dimen.radius_8)

        binding.tvPlaylistName.text = model.playlistName
        binding.tvTracksCount.text = itemView.resources.getQuantityString(R.plurals.tracks, model.tracksCount, model.tracksCount)

        binding.ivPlaylistCover.setImage(
            url = model.coverImageUrl,
            placeholder = R.drawable.placeholder,
            cornerRadius = cornerRadius,
        )
    }
}
