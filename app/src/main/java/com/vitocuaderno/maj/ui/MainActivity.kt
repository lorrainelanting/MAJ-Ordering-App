package com.vitocuaderno.maj.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.vitocuaderno.maj.R
import com.vitocuaderno.maj.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var navController: NavController
    private lateinit var mPager: ViewPager

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPager = binding.viewPager
        //Getting the Navigation Controller
        navController = Navigation.findNavController(this, R.id.fragment)
        //Setting the navigation controller to Bottom Nav
        binding.navBottom.setupWithNavController(navController)
        //Setting up the action bar
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    //Setting Up the back button
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    /**
     * Private functions below
     * */

}