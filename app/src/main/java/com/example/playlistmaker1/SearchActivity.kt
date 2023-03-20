package com.example.playlistmaker1

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker1.SearchHistory.Companion.KEY_LIST_TRACKS
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {


    companion object {
        var text: String? = ""
    }

    lateinit var inputEditText: EditText
    lateinit var clearIconButton: ImageView
    lateinit var recyclerView: RecyclerView
    //lateinit var recyclerViewHistory: RecyclerView
    lateinit var refreshButton: ImageView
    lateinit var clearHistory: Button
    lateinit var textHistory: TextView
    var sharedPreferencesCopy: SharedPreferences? = null
    private val searchHistory: SearchHistory by lazy{
            SearchHistory(sharedPreferencesCopy!!)
    }
    private lateinit var noConnectError: LinearLayout
    lateinit var noSearchError: LinearLayout
    private val appleBaseUrl = "https://itunes.apple.com"

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(appleBaseUrl)
        .addConverterFactory(GsonConverterFactory.create()).build()

    private val appleService = retrofit.create<AppleApi>()

    private val track = ArrayList<Track>()
    private val adapter = TrackAdapter()

    //private val adapterHistory = TrackAdapter()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        sharedPreferencesCopy = getSharedPreferences(SearchHistory.KEY_LIST_TRACKS, MODE_PRIVATE)


        inputEditText = findViewById(R.id.inputEditText)
        clearIconButton = findViewById(R.id.clearIconButton)
        recyclerView = findViewById(R.id.recycler_view)
        //recyclerViewHistory = findViewById(R.id.recycler_view_history)
        refreshButton = findViewById(R.id.refreshButton)
        noSearchError = findViewById(R.id.noSearchError)
        clearHistory = findViewById(R.id.clearHistory)
        textHistory = findViewById(R.id.textHistory)
        noConnectError = findViewById(R.id.noConnectError)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        showHistory()
        visibleInvisibleClearButton(inputEditText, clearIconButton)

        if (savedInstanceState != null) {

            text = savedInstanceState.getString("textSearch")
            if (!text.isNullOrEmpty()) {
                inputEditText.setText(text)
            }
        }

        clearHistory.setOnClickListener{
            val sharedPref = getSharedPreferences(KEY_LIST_TRACKS, MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.clear()
            textHistory.visibility = View.INVISIBLE
            clearHistory.visibility = View.INVISIBLE
            editor.apply()
            adapter.track = ArrayList()
            adapter.notifyDataSetChanged()
        }


        // кнопка назад
        findViewById<ImageView>(R.id.arrow_back).setOnClickListener {
            finish()
        }

        //кнопка DONE
        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchTrack(noConnectError, noSearchError)
                clearHistory.visibility = View.INVISIBLE
                true
            }
            false
        }
        //кнопка обновить поиск
        refreshButton.setOnClickListener {
            adapter.deleteList(track, adapter)
            noConnectError.visibility = View.INVISIBLE
            noSearchError.visibility = View.INVISIBLE
            refreshButton.visibility = View.INVISIBLE
            searchTrack(noSearchError, noConnectError)
        }


        //фокус на поле ввода
        inputEditText.setOnFocusChangeListener { _, hasFocus ->
            adapter.track = ArrayList()
            showHistory()
            adapter.notifyDataSetChanged()
            textHistory.visibility = if (hasFocus) View.VISIBLE else View.INVISIBLE
            clearHistory.visibility = if (hasFocus) View.VISIBLE else View.INVISIBLE
        }
        adapter.history = searchHistory




        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                visibleInvisibleClearButton(inputEditText, clearIconButton)
                text = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {}
        }

        inputEditText.addTextChangedListener(simpleTextWatcher)

        //Крестик убирает клавиатуру и очищает поле ввода
        clearIconButton.setOnClickListener {
            inputEditText.text.clear()
            noSearchError.visibility = View.INVISIBLE
            noConnectError.visibility = View.INVISIBLE
            showHistory()
            adapter.notifyDataSetChanged()
            //recyclerView.adapter = adapter
            this.currentFocus?.let { view ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
            visibleInvisibleClearButton(inputEditText, clearIconButton)
        }

    }

    private fun showHistory() {
        val currentList = ArrayList(SearchHistory(getSharedPreferences(KEY_LIST_TRACKS, MODE_PRIVATE)).get().toList())
        if(currentList.isNotEmpty()){
            textHistory.visibility = View.VISIBLE
            clearHistory.visibility = View.VISIBLE
            adapter.notifyDataSetChanged()
        }
        adapter.track = currentList
        adapter.notifyDataSetChanged()
    }


    //поиск треков API
    private fun searchTrack(noSearchError: LinearLayout, noConnectError: LinearLayout) {
        appleService.search(inputEditText.text.toString())
            .enqueue(object : Callback<AppleResponse> {
                override fun onResponse(
                    call: Call<AppleResponse>,
                    response: Response<AppleResponse>
                ) {
                    if (response.isSuccessful) {

                        if (response.body()?.results?.isNotEmpty() == true) {
                            adapter.deleteList(track, adapter)
                            track.addAll(response.body()?.results!!)
                            recyclerView.visibility = View.VISIBLE
                            adapter.track = track
                            adapter.notifyDataSetChanged()


                        } else {
                            adapter.deleteList(track, adapter)
                            noSearchError.visibility = View.VISIBLE
                            noConnectError.visibility = View.INVISIBLE
                            refreshButton.visibility = View.VISIBLE

                        }

                    } else {
                        adapter.deleteList(track, adapter)
                        noSearchError.visibility = View.INVISIBLE
                        noConnectError.visibility = View.VISIBLE
                        refreshButton.visibility = View.VISIBLE

                    }
                }

                override fun onFailure(call: Call<AppleResponse>, t: Throwable) {
                    t.printStackTrace()
                    adapter.deleteList(track, adapter)
                    noConnectError.visibility = View.VISIBLE
                    refreshButton.visibility = View.VISIBLE
                }
            })
    }

    private fun visibleInvisibleClearButton(inputEditText: EditText, clearIconButton: ImageView) {
        clearIconButton.isVisible = inputEditText.text.isNotEmpty()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("textSearch", text)
    }
}