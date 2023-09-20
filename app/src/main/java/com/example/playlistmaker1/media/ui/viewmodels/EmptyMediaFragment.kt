package com.example.playlistmaker1.media.ui.viewmodels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import com.example.playlistmaker1.R
import com.example.playlistmaker1.databinding.FragmentEmptyMediaBinding
import org.koin.android.ext.android.getKoin

class EmptyMediaFragment : Fragment() {

    private var STATE = StateMediatekaFragment.DEFAULT

    companion object {
        fun newInstance(state: StateMediatekaFragment) = EmptyMediaFragment().apply {
            STATE = state
        }
    }

    private var _binding: FragmentEmptyMediaBinding? = null
    private val binding get() = _binding!!
    private val viewModel : EmptyMediaFragmentModel = getKoin().get()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmptyMediaBinding.inflate(inflater, container, false)

        when (STATE) {

            StateMediatekaFragment.FAVORITE -> {
                binding.newPlayList.isGone = true
                binding.text.text = getString(R.string.emptyMedia)
            }
            StateMediatekaFragment.PLAYLISTS -> {
                binding.text.text = getString(R.string.emptyPlayLists)
            }
            StateMediatekaFragment.DEFAULT -> {

            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}