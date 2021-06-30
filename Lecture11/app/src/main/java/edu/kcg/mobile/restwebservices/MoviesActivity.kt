package edu.kcg.mobile.restwebservices

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import edu.kcg.mobile.restwebservices.model.Movie
import edu.kcg.mobile.restwebservices.model.MovieSearchResult
import edu.kcg.mobile.restwebservices.view.MovieViewAdapter
import org.json.JSONObject

class MoviesActivity : AppCompatActivity() {

    private lateinit var viewAdapter: MovieViewAdapter
    private lateinit var etMovieName: TextView
    private lateinit var tvTotalResults: TextView
    private lateinit var textView1: TextView

    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        findViewById<Button>(R.id.button_search_movies).setOnClickListener { send(it as Button) }
        etMovieName = findViewById(R.id.et_movie_name)
        tvTotalResults = findViewById(R.id.tv_total_results)
        textView1 = findViewById(R.id.text_view1)

        tvTotalResults.text = getString(R.string.total_results, "-")

        val viewManager = LinearLayoutManager(this)
        viewAdapter = MovieViewAdapter(this, emptyList())

        findViewById<RecyclerView>(R.id.locations_list).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            setOnClickListener {  }
        }
    }

    private fun send(button: Button) {
        val queue = Volley.newRequestQueue(this)

        val movieName = etMovieName.text.toString().let {
            if (it.isBlank()) "Star Wars" else it
        }

        val url2 = "https://www.omdbapi.com/?apikey=da03bdf7&s=$movieName&r=json"
        val moviesJsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url2,
            null,
            { response -> processResponse(response) },
            { error -> textView1.text = getString(R.string.error); println(error) }
        )
        queue.add(moviesJsonObjectRequest)
    }

    private fun processResponse(response: JSONObject?) {
        val result = gson.fromJson(response.toString(), MovieSearchResult::class.java)
        tvTotalResults.text = getString(R.string.total_results, result.totalResults ?: "0")
        textView1.text = if (result.Search?.isEmpty() == true) "No results" else ""

        viewAdapter.data = result.Search ?: emptyList<Movie>()
        viewAdapter.notifyDataSetChanged()
    }

}