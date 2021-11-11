package edu.kcg.mobile.lecture05app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import edu.kcg.mobile.lecture05app.R
import edu.kcg.mobile.lecture05app.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = root.findViewById(R.id.text_settings)
//        textView.textSize = 40f
        textView.text = "Settings fragment"

        return root
    }

}