package com.kailas.kb_hsbc_coding_challenge.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kailas.kb_hsbc_coding_challenge.views.fragments.AllUserFragment
import com.kailas.kb_hsbc_coding_challenge.views.fragments.FavoriteUserFragment

private const val NUM_TABS = 2

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return AllUserFragment()
            1 -> return FavoriteUserFragment()
        }
        return AllUserFragment()
    }
}