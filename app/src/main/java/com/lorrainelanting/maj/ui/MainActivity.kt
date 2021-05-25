package com.lorrainelanting.maj.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import androidx.viewpager.widget.ViewPager
import com.google.android.material.badge.BadgeDrawable
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.CartContent
import com.lorrainelanting.maj.data.model.Order
import com.lorrainelanting.maj.data.repository.cart.CartRepository
import com.lorrainelanting.maj.data.repository.order.OrderRepository
import com.lorrainelanting.maj.databinding.ActivityMainBinding
import com.lorrainelanting.maj.di.Injection
import com.lorrainelanting.maj.ui.base.BaseActivity
import com.lorrainelanting.maj.ui.cart.CartFragment
import com.lorrainelanting.maj.ui.viewpager.ViewPagerAdapter

class MainActivity : BaseActivity<ActivityMainBinding>(), CartFragment.CartFragmentListener {
    lateinit var cartRepository: CartRepository
    lateinit var orderRepository: OrderRepository
    private val cartContentsLiveData by lazy {
        cartRepository.getList()
    }

    private val ordersContentLiveData by lazy {
        orderRepository.getList()
    }

    private lateinit var navController: NavController
    private lateinit var mPager: ViewPager
    private lateinit var badge: BadgeDrawable

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPager = binding.viewPager

        mPager = binding.viewPager
        val pagerAdapter = ViewPagerAdapter(supportFragmentManager, this.resources, this)
        mPager.adapter = pagerAdapter
        cartRepository = Injection.provideCartRepository(this)
        cartContentsLiveData.observe(this) { it ->
            setBadgeCart(it)
        }

        orderRepository = Injection.provideOrderRepository(this)
        ordersContentLiveData.observe(this) {
            setBadgeOrders(it)
        }

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

    override fun onContinueShoppingClick() {
        navigateToHome()
    }

    override fun onSetProfileClick() {
        navigateToProfile()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            2 -> {
                setResult(2)
                mPager.currentItem = 2 // OrdersFragment
            }
            4 -> {
                setResult(4)
                mPager.currentItem = 4 // ProfileFragment
            }
        }
    }

    /**
     * Private functions below
     * */

    private fun setBadgeCart(contents: List<CartContent>) {
        badge = binding.navBottom.getOrCreateBadge(R.id.itemFragmentCart)
        if (contents.isNotEmpty()) {
            badge.isVisible = true
            badge.number = contents.size
        } else {
            badge.isVisible = false
        }
    }

    private fun setBadgeOrders(contents: List<Order>) {
        badge = binding.navBottom.getOrCreateBadge(R.id.itemFragmentOrders)
        if (contents.isNotEmpty()) {
            badge.isVisible = true
            badge.number = contents.size
        } else {
            badge.isVisible = false
        }
    }

    private fun navigateToHome() {
        mPager.currentItem = 0
    }

    private fun navigateToProfile() {
        mPager.currentItem = 4
    }
}