package edu.kcg.mobile.restwebservices.model

data class QuoteContainer(
    var type: String,
    var value: Quote,
)

data class Quote(
    var id: Long,
    var quote: String
)
