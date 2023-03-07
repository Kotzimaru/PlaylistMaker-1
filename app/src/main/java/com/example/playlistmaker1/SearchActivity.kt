package com.example.playlistmaker1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.core.view.isVisible

class SearchActivity : AppCompatActivity() {

    companion object {
        var text: String? = "";
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        val inputEditText = findViewById<EditText>(R.id.inputEditText)
        val clear = findViewById<ImageView>(R.id.clearButton)

        findViewById<ImageView>(R.id.arrow_back).setOnClickListener {
            finish()
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
                    visibleInvisibleClearButton(inputEditText, clear)
                    text = p0.toString();
                }

                override fun afterTextChanged(p0: Editable?) {}
            }

            inputEditText.addTextChangedListener(simpleTextWatcher)

            clear.setOnClickListener {
                inputEditText.text.clear()
                this.currentFocus?.let { view ->
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(view.windowToken, 0)
                }
                visibleInvisibleClearButton(inputEditText, clear)
            }

        }

        override fun onSaveInstanceState(outState: Bundle) {
            super.onSaveInstanceState(outState)
            outState.putString("textSearch", text)
        }


        private fun visibleInvisibleClearButton(inputEditText: EditText, clear: ImageView) {

            clear.isVisible = !inputEditText.text.isEmpty()

        }

    }

