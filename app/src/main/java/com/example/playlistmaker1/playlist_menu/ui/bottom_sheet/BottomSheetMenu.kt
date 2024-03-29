package com.example.playlistmaker1.playlist_menu.ui.bottom_sheet

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker1.R
import com.example.playlistmaker1.core.utils.setImage
import com.example.playlistmaker1.core.utils.viewBinding
import com.example.playlistmaker1.databinding.BottomSheetMenuBinding
import com.example.playlistmaker1.playlist_creator.domain.models.PlaylistModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.playlistmaker1.playlist_menu.ui.fragment.MessageCreator
import com.example.playlistmaker1.playlist_menu.ui.view_model.PlaylistMenuViewModel
import com.example.playlistmaker1.playlist_redactor.ui.fragment.PlaylistRedactorFragment
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetMenu : BottomSheetDialogFragment(R.layout.bottom_sheet_menu) {

    private val binding by viewBinding<BottomSheetMenuBinding>()
    private val viewModel by viewModel<PlaylistMenuViewModel>()

    private var playlist: PlaylistModel? = null

    override fun onStart() {
        super.onStart()
        val bottomSheetSetuper = BottomSheetSetuper(activity)
        bottomSheetSetuper.setupRatio(dialog as BottomSheetDialog, PERCENT_OCCUPIED_BY_BOTTOM_SHEET)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playlist = requireArguments()
            .getString(PLAYLIST_KEY)
            ?.let { Json.decodeFromString<PlaylistModel>(it) } ?: PlaylistModel.emptyPlaylist

        playlist?.let { drawPlaylistItem(it) }

        initListeners()
    }

    private fun drawPlaylistItem(playlist: PlaylistModel) {

        with(binding) {
            bottomSheetMenuItem.ivCover.setImage(
                url = playlist.coverImageUrl,
                placeholder = R.drawable.placeholder,
            )

            bottomSheetMenuItem.tvPlaylistName.text = playlist.playlistName

            bottomSheetMenuItem.tvTracksCount.text = resources.getQuantityString(
                R.plurals.tracks, playlist.tracksCount, playlist.tracksCount
            )
        }
    }

    private fun initListeners() {
        with(binding) {
            tvShare.setOnClickListener { sharePlaylist() }
            tvEdit.setOnClickListener { openEditor() }
            tvDelete.setOnClickListener { showDialog() }
        }
    }

    private fun openEditor() {
        findNavController().navigate(
            R.id.action_bottomSheetMenu_to_playlistRedactorFragment,
            playlist?.let {
                PlaylistRedactorFragment.createArgs(it)
            })
    }

    private fun sharePlaylist() {
        if (playlist?.trackList.isNullOrEmpty()) {
            showMessage()
        } else {
            val messageCreator = MessageCreator(requireContext())

            playlist?.let {
                viewModel.sharePlaylist(messageCreator.create(it))
            }
        }
    }

    private fun showMessage() {
        val message = getString(R.string.empty_track_list)
        Toast
            .makeText(requireContext(), message, Toast.LENGTH_SHORT)
            .show()
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete_playlist) + " ${playlist?.playlistName}?")
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                playlist?.let { viewModel.deletePlaylist(it) }
                goBack()

            }
            .show()
    }

    private fun goBack() {
        findNavController().popBackStack(R.id.mediaFragment, false)
    }

    companion object {

        const val PLAYLIST_KEY = "playlist_key"
        private const val PERCENT_OCCUPIED_BY_BOTTOM_SHEET = 0.45f

        fun createArgs(playlist: PlaylistModel): Bundle = bundleOf(
            PLAYLIST_KEY to Json.encodeToString(playlist)
        )
    }
}