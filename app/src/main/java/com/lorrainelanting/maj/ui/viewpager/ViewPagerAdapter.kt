package com.lorrainelanting.maj.ui.viewpager

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.ui.home.HomeFragment
import com.lorrainelanting.maj.ui.LoyaltyPointsFragment
import com.lorrainelanting.maj.ui.order.OrdersFragment
import com.lorrainelanting.maj.ui.profile.ProfileFragment
import com.lorrainelanting.maj.ui.cart.CartFragment

class ViewPagerAdapter constructor (
    fm: FragmentManager,
    val resources : Resources,
    private val cartFListener : CartFragment.CartFragmentListener? = null) : FragmentPagerAdapter(fm) {
    companion object {
        const val NUM_PAGES = 5
    }

    override fun getCount(): Int =
        NUM_PAGES

    override fun getItem(position: Int): Fragment {
        return when (position) {
//            0 -> HomeFragment()
            1 -> LoyaltyPointsFragment()
            2 -> OrdersFragment()
            3 -> {
                val fragment = CartFragment()
                cartFListener?.let { fragment.setCartFragmentListener(it) }
                return fragment
            }
            4 -> ProfileFragment()
            else -> HomeFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> resources.getString(R.string.app_name)
            1 -> resources.getString(R.string.points)
            2 -> resources.getString(R.string.orders)
            3 -> resources.getString(R.string.cart)
            4 -> resources.getString(R.string.profile)
            else -> super.getPageTitle(position)
        }

    }
}