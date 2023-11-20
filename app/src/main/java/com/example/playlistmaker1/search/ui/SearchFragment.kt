package com.example.playlistmaker1.search.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker1.*
import com.example.playlistmaker1.databinding.FragmentSearchBinding
import com.example.playlistmaker1.core.ui.HostActivity
import com.example.playlistmaker1.core.utils.debounce
import com.example.playlistmaker1.core.utils.viewBinding
import com.example.playlistmaker1.player.ui.PlayerFragment
import com.example.playlistmaker1.search.domain.NetworkError
import com.example.playlistmaker1.search.domain.api.TrackModel

import com.example.playlistmaker1.search.ui.viewmodels.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding<FragmentSearchBinding>()
    private val viewModel by viewModel<SearchViewModel>()

    private var trackAdapter: TrackAdapter? = null
    private var onClickDebounce: ((TrackModel) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickDebounce = debounce(delayMillis = CLICK_DEBOUNCE_DELAY_MILLIS,
            coroutineScope = viewLifecycleOwner.lifecycleScope,
            useLastParam = false,
            action = { track ->
                viewModel.addTrackToHistoryList(track)
                findNavController().navigate(
                    R.id.action_searchFragment_to_playerFragment,
                    PlayerFragment.createArgs(track)
                )
            })

        with(viewModel) {
            observeContentState().observe(viewLifecycleOwner) { searchScreenState ->
                render(searchScreenState)
            }
            observeClearIconState().observe(viewLifecycleOwner) { query ->
                clearIconVisibility(query)
            }
            observeSearchTextClearClicked().observe(viewLifecycleOwner) { isClicked ->
                if (isClicked) {
                    clearSearchText()
                    hideKeyboard()

                }
            }
        }

        initListeners()
        initAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        trackAdapter = null
    }

    private fun initListeners() {

        with(binding) {

            etInput.setOnFocusChangeListener { _, hasFocus ->
                viewModel.searchFocusChanged(hasFocus, binding.etInput.text.toString())
            }
            etInput.doOnTextChanged { text, _, _, _ ->
                viewModel.onSearchTextChanged(text.toString())
            }
            ivClear.setOnClickListener {
                viewModel.searchTextClearClicked()
            }
            clearHistoryBtn.setOnClickListener {
                viewModel.onHistoryClearedClicked()
            }
            updateButton.setOnClickListener {
                viewModel.loadTrackList(binding.etInput.text.toString())
            }
        }
    }

    private fun initAdapter() {
        trackAdapter = TrackAdapter(
            clickListener = (TrackAdapter.TrackClickListener { track ->
                (activity as HostActivity).animateBottomNavigationView()
                onClickDebounce?.let { it(track) }
            }),
        )

        binding.rvTrackList.adapter = trackAdapter
    }

    private fun render(state: SearchContentState) {
        when (state) {
            is SearchContentState.Loading ->
                showLoading()
            is SearchContentState.Error -> showMessage(state.error)
            is SearchContentState.SearchContent -> {
                showSearchList(state.trackList)
            }
            is SearchContentState.HistoryContent -> {
                showHistoryList(state.historyList)
            }
        }
    }

    private fun showSearchList(list: List<TrackModel>) {

        with(binding) {
            ivPlaceholder.visibility = View.GONE
            tvPlaceholder.visibility = View.GONE
            updateButton.visibility = View.GONE
            tvYouSearched.visibility = View.GONE
            clearHistoryBtn.visibility = View.GONE
            progressBar.visibility = View.GONE
        }

        refreshList(list)
    }

    private fun showHistoryList(list: List<TrackModel>) {

        with(binding) {
            ivPlaceholder.visibility = View.GONE
            tvPlaceholder.visibility = View.GONE
            updateButton.visibility = View.GONE
            progressBar.visibility = View.GONE

            if (list.isNotEmpty()) {
                tvYouSearched.visibility = View.VISIBLE
                clearHistoryBtn.visibility = View.VISIBLE
            } else {
                tvYouSearched.visibility = View.GONE
                clearHistoryBtn.visibility = View.GONE
            }
        }

        refreshList(list)
    }

    private fun showMessage(error: NetworkError) {
        refreshList(emptyList())

        when (error) {
            NetworkError.SEARCH_ERROR -> {
                with(binding) {
                    ivPlaceholder.visibility = View.VISIBLE
                    tvPlaceholder.visibility = View.VISIBLE
                    updateButton.visibility = View.GONE
                    tvYouSearched.visibility = View.GONE
                    clearHistoryBtn.visibility = View.GONE
                    progressBar.visibility = View.GONE

                    tvPlaceholder.text = getString(R.string.search_error)
                    ivPlaceholder.setImageDrawable(
                        AppCompatResources.getDrawable(requireContext(), R.drawable.error_search)
                    )
                }
            }

            NetworkError.CONNECTION_ERROR -> {
                hideKeyboard()

                with(binding) {
                    ivPlaceholder.visibility = View.VISIBLE
                    tvPlaceholder.visibility = View.VISIBLE
                    updateButton.visibility = View.VISIBLE
                    tvYouSearched.visibility = View.GONE
                    clearHistoryBtn.visibility = View.GONE
                    progressBar.visibility = View.GONE

                    tvPlaceholder.text = getString(R.string.internet_error)
                    ivPlaceholder.setImageDrawable(
                        AppCompatResources.getDrawable(
                            requireContext(), R.drawable.error_internet
                        )
                    )
                }
            }
        }
    }

    private fun showLoading() {
        refreshList(emptyList())

        with(binding) {
            ivPlaceholder.visibility = View.GONE
            tvPlaceholder.visibility = View.GONE
            updateButton.visibility = View.GONE
            tvYouSearched.visibility = View.GONE
            clearHistoryBtn.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun refreshList(trackList: List<TrackModel>) {
        trackAdapter?.let {
            it.trackList.clear()
            it.trackList.addAll(trackList)
            it.notifyDataSetChanged()
        }
    }

    private fun clearSearchText() {
        binding.etInput.setText("")
    }

    private fun clearIconVisibility(query: String?) {
        if (query.isNullOrEmpty()) binding.ivClear.visibility = View.GONE
        else binding.ivClear.visibility = View.VISIBLE
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 300L
    }
}