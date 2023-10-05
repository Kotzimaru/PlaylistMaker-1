package com.example.playlistmaker1.search.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker1.*
import com.example.playlistmaker1.databinding.FragmentSearchBinding
import com.example.playlistmaker1.player.ui.PlayerFragment
import com.example.playlistmaker1.search.domain.api.StateSearch
import com.example.playlistmaker1.search.ui.viewmodels.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.*

class SearchFragment : Fragment() {


    lateinit var textHistory: TextView
    lateinit var inputEditText: EditText
    lateinit var clearIconButton: ImageView
    lateinit var recyclerView: RecyclerView
    lateinit var refreshButton: ImageView
    lateinit var clearHistory: FrameLayout
    lateinit var noSearchError: LinearLayout
    lateinit var noConnectError: LinearLayout
    lateinit var progressBar: ProgressBar
    private lateinit var binding: FragmentSearchBinding
    private var text: String = ""
    private var isClick = true
    private var trackAdapter = TrackAdapter()

    private val viewModel: SearchViewModel by viewModel()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getState().observe(viewLifecycleOwner) {

            if (it.second == StateSearch.SHOW_UPLOAD_TRACKS) {
                progressBar.isGone = true
                noSearchError.isGone = true
                noConnectError.isGone = true
                clearHistory.isGone = true
                textHistory.isGone = true
                trackAdapter.track = it.first!!
                trackAdapter.notifyDataSetChanged()
                recyclerView.isGone = false
            } else if (it.second == StateSearch.EMPTY_UPLOAD_TRACKS) {
                noSearchError.visibility = View.VISIBLE
                noConnectError.isGone = true
                progressBar.isGone = true
            } else if (it.second == StateSearch.NO_CONNECTION) {
                noConnectError.visibility = View.VISIBLE
                progressBar.isGone = true
            } else if (it.second == StateSearch.SHOW_HISTORY) {
                if (it.first?.isEmpty() == false) {
                    clearHistory.visibility = View.VISIBLE
                    textHistory.visibility = View.VISIBLE
                    trackAdapter.track = it.first!!
                    trackAdapter.notifyDataSetChanged()
                }
            } else if (it.second == StateSearch.EMPTY_HISTORY) {
                clearHistory.isGone = true
                textHistory.isGone = true
            } else if (it.second == StateSearch.SEARCH) {
                progressBar.isGone = false
                recyclerView.isGone = true
                clearHistory.isGone = true
                textHistory.isGone = true
            }

        }

        noSearchError = binding.root.findViewById(R.id.noSearchError)
        noConnectError = binding.root.findViewById(R.id.noConnectError)
        inputEditText = binding.root.findViewById(R.id.inputEditText)
        clearIconButton = binding.root.findViewById(R.id.clearIconButton)
        recyclerView = binding.root.findViewById(R.id.recycler_view)
        refreshButton = binding.root.findViewById(R.id.refreshButton)
        clearHistory = binding.root.findViewById(R.id.clearHistory)
        textHistory = binding.root.findViewById(R.id.textHistory)
        progressBar = binding.root.findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = trackAdapter

        visibleInvisibleClearButton(inputEditText, clearIconButton)

        if (savedInstanceState != null) {
            text = savedInstanceState.getString("textSearch").toString()
            if (!text.isNullOrEmpty()) inputEditText.setText(text)
        }

        inputEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) viewModel.getHistory()
        }

        trackAdapter.itemClickListener = { _, track ->
            viewModel.removeTrack(track)
            trackAdapter.notifyDataSetChanged()

            // Переход на экран плеера
            findNavController().navigate(
                R.id.action_searchFragment_to_playerFragment,
                PlayerFragment.createArgs(viewModel.trackToJSON(track))
            )
        }

        refreshButton.setOnClickListener { viewModel.uploadTracks(text) }

        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                textHistory.isGone = true
                clearHistory.isGone = true
                progressBar.isGone = false
                recyclerView.isGone = true
                viewModel.uploadTracks(text)
                progressBar.visibility = View.VISIBLE
            }
            false
        }

        clearHistory.setOnClickListener {
            if (clickDebounse()) {
                viewModel.clear()
                trackAdapter.track = ArrayList()
                trackAdapter.notifyDataSetChanged()
                clearHistory.isGone = true
                textHistory.isGone = true
                viewModel.setHistory()
            }
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                visibleInvisibleClearButton(inputEditText, clearIconButton)
                text = p0.toString()
                if (text.isNotEmpty()) {
                    noSearchError.isGone = true
                    noConnectError.isGone = true
                    searchDebounse()
                } else {
                    clearHistory.visibility = View.VISIBLE
                    textHistory.visibility = View.VISIBLE
                    viewModel.getHistory()
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        }

        inputEditText.addTextChangedListener(simpleTextWatcher)
        textHistory.setOnClickListener {
            inputEditText.text.clear()
            noSearchError.isGone = true
            noConnectError.isGone = true
            trackAdapter.track = ArrayList()
            trackAdapter.notifyDataSetChanged()
            recyclerView.adapter = trackAdapter
/*            this.currentFocus?.let { view ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }*/
            visibleInvisibleClearButton(inputEditText, clearIconButton)
        }

        //Крестик убирает клавиатуру и очищает поле ввода
        clearIconButton.setOnClickListener {
            inputEditText.text.clear()
            noSearchError.visibility = View.INVISIBLE
            noConnectError.visibility = View.INVISIBLE
            viewModel.getHistory()
            recyclerView.adapter = trackAdapter
/*            this.currentFocus?.let { view ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }*/
            visibleInvisibleClearButton(inputEditText, clearIconButton)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("textSearch", text)
    }

    private fun visibleInvisibleClearButton(search: EditText, clear: ImageView) {
        clear.isVisible = search.text.isNotEmpty()
    }

    private fun searchDebounse() {
        viewModel.searchDebounse(text)
    }

    private fun clickDebounse(): Boolean {
        val current = isClick
        if (isClick) {
            isClick = false
            isClick = viewModel.clickDebounse()
        }
        return current
    }


}