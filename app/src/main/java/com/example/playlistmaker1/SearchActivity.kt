package com.example.playlistmaker1

import android.content.Context
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {


    companion object {
        var text: String? = "";
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val inputEditText = findViewById<EditText>(R.id.inputEditText)
        val clearButton = findViewById<ImageView>(R.id.clearButton)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        //val placeholderMessage = findViewById(R.id.placeholderMessage)
        //lateinit var placeholderMessage: TextView
        val appleBaseUrl = "https://itunes.apple.com/"
        val refreshButton = findViewById<Button>(R.id.refreshButton)
        val noSearchError = findViewById<LinearLayout>(R.id.noSearchError)
        val noConnectError = findViewById<LinearLayout>(R.id.noConnectError)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val retrofit = Retrofit.Builder()
            .baseUrl(appleBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val appleService = retrofit.create(AppleApi::class.java)
        val track = ArrayList<Track>()
        val adapter = TrackAdapter()


        recyclerView.adapter = adapter


        findViewById<ImageView>(R.id.arrow_back).setOnClickListener {
            finish()
        }

        /*fun showMessage(text: String, additionalMessage: String) {
            if (text.isNotEmpty()) {
                noSearchError.visibility = View.VISIBLE
                adapter.deleteList(track, adapter)
                adapter.notifyDataSetChanged()
                getString(R.string.nothing_found)

                *//*if (additionalMessage.isNotEmpty()) {
                    Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG)
                        .show()
                }*//*
            } else {
                noSearchError.visibility = View.GONE
            }
        }*/


        fun searchTrack() {
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


                            } else {
                                noSearchError.visibility = View.VISIBLE
                                noConnectError.visibility = View.INVISIBLE


                            }

                        } else {
                            noSearchError.visibility = View.INVISIBLE
                            noConnectError.visibility = View.VISIBLE
                            refreshButton.visibility = View.VISIBLE

                        }
                    }

                    override fun onFailure(call: Call<AppleResponse>, t: Throwable) {
                        t.printStackTrace()
                        //noSearchError.visibility = View.INVISIBLE
                        noConnectError.visibility = View.VISIBLE
                        //refreshButton.visibility = View.VISIBLE
                    }
                })
        }






        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchTrack()// ВЫПОЛНЯЙТЕ ПОИСКОВЫЙ ЗАПРОС ЗДЕСЬ
                true
            }
            false
        }

        refreshButton.setOnClickListener {
            searchTrack()
            noConnectError.visibility = View.INVISIBLE
            noSearchError.visibility = View.INVISIBLE
            //placeHolderMessage.visibility = View.INVISIBLE
            refreshButton.visibility = View.INVISIBLE

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

        clearButton.setOnClickListener {
            inputEditText.text.clear()
            this.currentFocus?.let { view ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
            visibleInvisibleClearButton(inputEditText, clearButton)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("textSearch", text)
    }

    fun visibleInvisibleClearButton(inputEditText: EditText, clear: ImageView) {

        clear.isVisible = !inputEditText.text.isEmpty()

    }

}

