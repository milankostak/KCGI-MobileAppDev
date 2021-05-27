package edu.kcg.mobile.layouts.ui.main

import androidx.lifecycle.ViewModel

class PageViewModel : ViewModel() {

    var index: Int = -1

    fun getText(): String {
        return "Greetings from section: $index"
    }

}