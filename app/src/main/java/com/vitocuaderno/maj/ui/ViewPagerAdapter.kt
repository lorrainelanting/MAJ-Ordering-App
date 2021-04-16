package com.vitocuaderno.maj.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.vitocuaderno.maj.R

class ViewPagerAdapter (fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    companion object {
        const val NUM_PAGES = 5

        val layoutFragments : Array<Int> = arrayOf(
            R.layout.fragment_home,
            R.layout.fragment_loyalty_points,
            R.layout.fragment_orders,
            R.layout.fragment_cart,
            R.layout.fragment_profile)
    }
    override fun getCount(): Int = NUM_PAGES

    override fun getItem(position: Int): Fragment = HomeFragment()
}