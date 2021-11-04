package edu.kcg.mobile.layouts.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import edu.kcg.mobile.layouts.R
import edu.kcg.mobile.layouts.ui.main.fragments.ConstraintFragment
import edu.kcg.mobile.layouts.ui.main.fragments.LinearFragment
import edu.kcg.mobile.layouts.ui.main.fragments.RelativeFragment

val TAB_TITLES = arrayOf(
    R.string.tab_linear,
    R.string.tab_relative,
    R.string.tab_constraint
)

const val ARG_SECTION_NUMBER = "section_number"

/**
 * A [FragmentStateAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 * Migration: https://developer.android.com/training/animation/vp2-migration
 */
class SectionsPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        // createFragment is called to instantiate the fragment for the given page.
        return when (position) {
            0 -> LinearFragment()
            1 -> RelativeFragment()
            else -> ConstraintFragment()
        }.apply {
            arguments = Bundle().apply {
                putInt(ARG_SECTION_NUMBER, position)
            }
        }
    }

    override fun getItemCount(): Int {
        // Returns number of tabs
        return TAB_TITLES.size
    }

}