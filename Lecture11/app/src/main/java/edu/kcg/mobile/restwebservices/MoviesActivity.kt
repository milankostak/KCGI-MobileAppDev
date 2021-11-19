package edu.kcg.mobile.restwebservices

import android.annotation.SuppressLint
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
import edu.kcg.mobile.restwebservices.model.MovieSearchResult
import edu.kcg.mobile.restwebservices.view.MovieViewAdapter
import org.json.JSONObject

class MoviesActivity : AppCompatActivity() {

    private lateinit var movieViewAdapter: MovieViewAdapter
    private lateinit var etMovieName: TextView
    private lateinit var tvTotalResults: TextView
    private lateinit var tvResults: TextView

    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        findViewById<Button>(R.id.button_search_movies).setOnClickListener { send() }
        etMovieName = findViewById(R.id.et_movie_name)
        tvTotalResults = findViewById(R.id.tv_total_results)
        tvResults = findViewById(R.id.tv_results)

        tvTotalResults.text = getString(R.string.total_results, "-")

        val viewManager = LinearLayoutManager(this)
        movieViewAdapter = MovieViewAdapter(this, emptyList())

        findViewById<RecyclerView>(R.id.movies_list).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = movieViewAdapter
        }
    }

    private fun send() {
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
            { error -> tvResults.text = getString(R.string.error); println(error) }
        )
        queue.add(moviesJsonObjectRequest)
    }

    @SuppressLint("NotifyDataSetChanged") // all items always change
    private fun processResponse(response: JSONObject?) {
        val searchResult = gson.fromJson(response.toString(), MovieSearchResult::class.java)
        tvTotalResults.text = getString(R.string.total_results, searchResult.totalResults ?: "0")
        tvResults.text = if (searchResult.Search?.isEmpty() == true) getString(R.string.no_results) else ""

        movieViewAdapter.data = searchResult.Search ?: emptyList()
        movieViewAdapter.notifyDataSetChanged()
    }

}