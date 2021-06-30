package edu.kcg.mobile.restwebservices.model

data class MovieSearchResult(
    val Search: List<Movie>?,
    val totalResults: String?,
    val Response: String
)

data class Movie(
    val Title: String,
    val Year: String,
    val imdbID: String,
    val Type: String,
    val Poster: String
)
