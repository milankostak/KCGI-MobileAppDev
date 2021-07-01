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

        val url1 = listOf(
            "https://en.wikipedia.org/wiki/JSON",
            "https://en.wikipedia.org/wiki/List_of_members_of_the_European_Parliament_for_Denmark,_2014%E2%80%932019",
            "https://en.wikipedia.org/wiki/P%C5%82o%C5%84sk_County",
            "https://en.wikipedia.org/wiki/%C5%BD%C4%8F%C3%A1r_nad_S%C3%A1zavou",
            "https://zh.wikipedia.org/wiki/%E8%96%A9%E6%89%8E%E7%93%A6%E6%B2%B3%E7%95%94%E6%97%A5%E8%B3%88%E7%88%BE",
            "https://ar.wikipedia.org/wiki/%D8%AC%D8%AF%D9%8A%D8%A7%D8%B1_%D9%86%D8%A7%D8%AF_%D8%B3%D8%A7%D8%B2%D8%A7%D9%81%D9%88"
        ).random()
        val stringRequest = StringRequest(
            Request.Method.GET,
            url1,
            { response -> processResponse1(response) },
            { error -> textView1.text = getString(R.string.error); println(error) }
        )
        queue.add(stringRequest)

        val url2 = "https://quoters.apps.pcfone.io/api/random"
        val quoteJsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url2,
            null,
            { response -> processResponse2(response) },
            { error -> textView2.text = getString(R.string.error); println(error) }
        )
//            .let { queue.add(it) }
        queue.add(quoteJsonObjectRequest)
    }

    private fun processResponse1(response: String) {
//        textView1.text = "Response is: ${response.substring(0, 40)}"

        // get the H1 heading from the HTML
        textView1.text =
            Regex("<h1 id=\"section_0\">(.+?)</h1>").find(response)?.groupValues?.get(1)
                ?: "missing h1"
    }


    private fun processResponse2(response: JSONObject?) {
        val quoteContainer = gson.fromJson(response.toString(), QuoteContainer::class.java)
        textView2.text = quoteContainer?.value?.quote ?: "quote container error"
    }

}