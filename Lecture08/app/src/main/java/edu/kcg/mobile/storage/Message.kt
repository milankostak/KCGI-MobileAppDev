package edu.kcg.mobile.storage

import java.io.Serializable

data class Message(val text: String, val count: Int = 0) : Serializable
