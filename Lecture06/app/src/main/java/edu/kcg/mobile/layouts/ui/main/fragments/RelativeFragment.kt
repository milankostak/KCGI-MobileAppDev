package edu.kcg.mobile.layouts.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import edu.kcg.mobile.layouts.R
import edu.kcg.mobile.layouts.ui.main.ARG_SECTION_NUMBER
import edu.kcg.mobile.layouts.ui.main.PageViewModel

class RelativeFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this)[PageViewModel::class.java].apply {
            index = arguments?.getInt(ARG_SECTION_NUMBER) ?: 0
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_relative, container, false)
        val textView = root.findViewById<TextView>(R.id.message_relative)
        textView.text = pageViewModel.getText()
        return root
    }

}