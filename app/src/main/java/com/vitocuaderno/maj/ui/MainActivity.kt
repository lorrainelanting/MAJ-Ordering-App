package com.vitocuaderno.maj.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import androidx.viewpager.widget.ViewPager
import com.google.android.material.badge.BadgeDrawable
import com.vitocuaderno.maj.R
import com.vitocuaderno.maj.databinding.ActivityMainBinding
import com.vitocuaderno.maj.ui.viewpager.ViewPagerAdapter

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var navController: NavController
    private lateinit var mPager: ViewPager
    private lateinit var badge: BadgeDrawable

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mPager = binding.viewPager

        //Getting the Navigation Controller
//        navController = Navigation.findNavController(this, R.id.fragment)
        //Setting the navigation controller to Bottom Nav
//        binding.navBottom.setupWithNavController(navController)

        mPager = binding.viewPager
        val pagerAdapter = ViewPagerAdapter(supportFragmentManager, this.resources)
        mPager.adapter = pagerAdapter

        setBadgeCart()
        setBadgeOrders()
//        ViewPager nav listener
        mPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
//                TODO("Not yet implemented")
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
//                TODO("Not yet implemented")
            }

            override fun onPageSelected(position: Int) {
                this@MainActivity.title =
                    (mPager.adapter as ViewPagerAdapter).getPageTitle(position)
                when (position) {
                    0 -> binding.navBottom.selectedItemId = R.id.itemFragmentHome
                    1 -> binding.navBottom.selectedItemId = R.id.itemFragmentLoyaltyPoints
                    2 -> binding.navBottom.selectedItemId = R.id.itemFragmentOrders
                    3 -> binding.navBottom.selectedItemId = R.id.itemFragmentCart
                    4 -> binding.navBottom.selectedItemId = R.id.itemFragmentProfile
                }

            }
        })

//        BottomNavigation listener
        binding.navBottom.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.itemFragmentHome -> mPager.currentItem = 0
                R.id.itemFragmentLoyaltyPoints -> mPager.currentItem = 1
                R.id.itemFragmentOrders -> mPager.currentItem = 2
                R.id.itemFragmentCart -> mPager.currentItem = 3
                R.id.itemFragmentProfile -> mPager.currentItem = 4
            }
            true
        }

        //Setting up the action bar
//        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    //Setting Up the back button
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    override fun onBackPressed() {
        if (mPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            mPager.currentItem = mPager.currentItem - 1
        }
    }

    /**
     * Private functions below
     * */

    private fun setBadgeCart() {
        badge = binding.navBottom.getOrCreateBadge(R.id.itemFragmentCart)
        badge.isVisible = true
        badge.number = 3 //    TODO: Set cart content size.

    }

    private fun setBadgeOrders() {
        badge = binding.navBottom.getOrCreateBadge(R.id.itemFragmentOrders)
        badge.isVisible = true
        badge.number = 1 //    TODO: Set active order size.
    }
}