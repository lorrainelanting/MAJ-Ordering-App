package com.vitocuaderno.maj

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.vitocuaderno.maj.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        setDrawerToggle()
        setClickNavItem()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_right_main, menu)

        val cartButton = menu.findItem(R.id.menu_cart)
        cartButton.icon = ContextCompat.getDrawable(this, R.drawable.ic_trolley)
        cartButton.icon.setTint(ContextCompat.getColor(this, R.color.white))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return when (item.itemId) {
            R.id.menu_cart -> {
                showCartActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_item_orders -> Toast.makeText(this, "TODO: Orders Activity", Toast.LENGTH_SHORT).show()
            R.id.nav_item_profile -> Toast.makeText(this, "TODO: Profile Activity", Toast.LENGTH_SHORT).show()
            R.id.nav_item_points -> Toast.makeText(this, "TODO: Points Activity", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    /**
     * Private functions
     * */

    private fun showCartActivity() {
        val intent = Intent(this, CartActivity::class.java).apply {
            putExtra("cart", getLayoutId())
        }
        startActivity(intent)
    }

    private fun setDrawerToggle() {
        //        val toolbar: Toolbar = findViewById(R.id.toolbar_main)

        drawer = findViewById(R.id.drawer_layout_main)
        toggle = ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun setClickNavItem() {
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }
}