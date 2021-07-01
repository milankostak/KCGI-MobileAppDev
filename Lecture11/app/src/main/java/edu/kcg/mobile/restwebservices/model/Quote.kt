package edu.kcg.mobile.restwebservices.model

data class QuoteContainer(
    val type: String?,
    val value: Quote?
)

data class Quote(
    val id: Int?,
    val quote: String?
)
