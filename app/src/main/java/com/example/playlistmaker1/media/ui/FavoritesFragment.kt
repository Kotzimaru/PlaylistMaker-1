package com.example.playlistmaker1.media.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker1.media.ui.viewmodels.FavoritesViewModel
import com.example.playlistmaker1.databinding.FragmentFavoritesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

    companion object {
        fun newInstance() = FavoritesFragment()
    }
    private val viewModel: FavoritesViewModel by viewModel()
    // TODO Позднее здесь будет ViewModel
    private lateinit var binding: FragmentFavoritesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
        // Логика для избранных треков будет здесь.
    }
}
