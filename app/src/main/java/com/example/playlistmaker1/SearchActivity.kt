package com.example.playlistmaker1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {


    companion object {
        var text: String? = "";
    }
    lateinit var inputEditText: EditText
    lateinit var clearButton: ImageView
    lateinit var recyclerView: RecyclerView
    lateinit var refreshButton: Button
    lateinit var noConnectError: LinearLayout
    lateinit var noSearchError: LinearLayout
    private val appleBaseUrl = "https://itunes.apple.com"

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(appleBaseUrl)
        .addConverterFactory(GsonConverterFactory.create()).build()

    private val appleService = retrofit.create<AppleApi>()

    private val track = ArrayList<Track>()
    private val adapter = TrackAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        inputEditText = findViewById(R.id.inputEditText)
        clearButton = findViewById(R.id.clearButton)
        recyclerView = findViewById(R.id.recycler_view)
        refreshButton = findViewById(R.id.refreshButton)
        noSearchError = findViewById(R.id.noSearchError)
        noConnectError = findViewById(R.id.noConnectError)

        recyclerView.layoutManager = LinearLayoutManager(this)


        recyclerView.adapter = adapter


        findViewById<ImageView>(R.id.arrow_back).setOnClickListener {
            finish()
        }

        refreshButton.setOnClickListener{
            searchTrack(noSearchError, noConnectError)
            noConnectError.visibility = View.INVISIBLE
            noSearchError.visibility = View.INVISIBLE
            refreshButton.visibility = View.INVISIBLE
        }

        fun visibleInvisibleClearButton(inputEditText: EditText, clear: ImageView) {
            clear.isVisible = !inputEditText.text.isEmpty()
        }

        clearButton.setOnClickListener{
            inputEditText.text.clear()
            this.currentFocus?.let { view ->
                val imm = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
            adapter.deleteList(track, adapter)
            visibleInvisibleClearButton(inputEditText, clearButton)
        }

        if (savedInstanceState != null) {

            text = savedInstanceState.getString("textSearch")
            if (!text.isNullOrEmpty()) {
                inputEditText.setText(text)
            }
        }


        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                visibleInvisibleClearButton(inputEditText, clearButton)
                text = p0.toString();
            }

            override fun afterTextChanged(p0: Editable?) {}
        }

        inputEditText.addTextChangedListener(simpleTextWatcher)

        inputEditText.setOnEditorActionListener{_, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchTrack(noSearchError, noConnectError)// ВЫПОЛНЯЙТЕ ПОИСКОВЫЙ ЗАПРОС ЗДЕСЬ
                true
            }
            false
        }

    }



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
                            noSearchError.visibility = View.VISIBLE
                            noConnectError.visibility = View.INVISIBLE
                            refreshButton.visibility = View.VISIBLE

                        }

                    } else {
                        noSearchError.visibility = View.INVISIBLE
                        noConnectError.visibility = View.VISIBLE
                        refreshButton.visibility = View.VISIBLE

                    }
                }

                override fun onFailure(call: Call<AppleResponse>, t: Throwable) {
                    t.printStackTrace()
                    noConnectError.visibility = View.VISIBLE
                    refreshButton.visibility = View.VISIBLE
                }
            })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("textSearch", text)
    }
}