package com.example.playlistmaker1.media.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.playlistmaker1.media.ui.viewmodels.EmptyMediaFragment
import com.example.playlistmaker1.media.ui.viewmodels.StateMediatekaFragment

class MediaViewPagerAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> EmptyMediaFragment.newInstance(StateMediatekaFragment.FAVORITE)
            1 -> EmptyMediaFragment.newInstance(StateMediatekaFragment.PLAYLISTS)
            else -> EmptyMediaFragment.newInstance(StateMediatekaFragment.DEFAULT)
        }
    }
}