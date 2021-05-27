package edu.kcg.mobile.layouts.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import edu.kcg.mobile.layouts.R
import edu.kcg.mobile.layouts.ui.main.fragments.ConstraintFragment
import edu.kcg.mobile.layouts.ui.main.fragments.LinearFragment
import edu.kcg.mobile.layouts.ui.main.fragments.RelativeFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_linear,
    R.string.tab_relative,
    R.string.tab_constraint
)

const val ARG_SECTION_NUMBER = "section_number"

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
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

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Returns number of tabs
        return TAB_TITLES.size
    }

}