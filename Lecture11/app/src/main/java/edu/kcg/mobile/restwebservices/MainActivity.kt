package edu.kcg.mobile.restwebservices

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import edu.kcg.mobile.restwebservices.model.QuoteContainer
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var textView1: TextView
    private lateinit var textView2: TextView
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_send).setOnClickListener { send() }
        textView1 = findViewById(R.id.text_view1)
        textView2 = findViewById(R.id.text_view2)

        findViewById<Button>(R.id.button_go_to_movies).setOnClickListener {
            val i = Intent(this, MoviesActivity::class.java)
            startActivity(i)
        }
    }

    private fun send() {
        val queue = Volley.newRequestQueue(this)

        val url1 = "https://www.google.com"
        val stringRequest = StringRequest(
            Request.Method.GET,
            url1,
            // Display the first 20 characters of the response string.
            { response -> textView1.text = "Response is: ${response.substring(0, 20)}" },
            { error -> textView1.text = getString(R.string.error); println(error) }
        )
        queue.add(stringRequest)

        val url2 = "https://quoters.apps.pcfone.io/api/random"
        val quoteJsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url2,
            null,
            { response -> processResponse(response) },
            { error -> textView2.text = getString(R.string.error); println(error) }
        )
        queue.add(quoteJsonObjectRequest)
    }

    private fun processResponse(response: JSONObject?) {
        val result = gson.fromJson(response.toString(), QuoteContainer::class.java)
        textView2.text = result.value.quote
    }

}