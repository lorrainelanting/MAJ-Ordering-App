package com.lorrainelanting.maj.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.observe
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.Product
import com.lorrainelanting.maj.databinding.FragmentHomeBinding
import com.lorrainelanting.maj.ui.base.BaseFragment
import com.lorrainelanting.maj.ui.common.LayoutAddToCart
import com.lorrainelanting.maj.ui.productdetail.ProductDetailActivity

class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeProductAdapter.HomeAdapterListener {
    var adapter: HomeProductAdapter? = null

    private val viewModel = HomeViewModel()
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    companion object {
        const val SORT_LOW_TO_HIGH = 0
        const val SORT_HIGH_TO_LOW = 1
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = HomeProductAdapter(emptyList(), this)
        binding.rvHomeContents.adapter = adapter

        setFilterToolbar()
        showHomeProducts()

        //Swipe refresh
        binding.swipeRefLayout.setOnRefreshListener {
            showHomeProducts()

            binding.swipeRefLayout.isRefreshing = false
        }
        binding.swipeRefLayout.setColorSchemeResources(R.color.colorSecondary)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.injectProduct(context)
        viewModel.injectCart(context)
    }

    override fun onItemClick(product: Product) {
        val intent = Intent(this.context, ProductDetailActivity().javaClass)
        intent.putExtra(ProductDetailActivity.ID, product.id)
        viewModel.repository.getItem(product.id)
        context?.startActivity(intent)
    }

    override fun onAddToCartBtnClick(product: Product) {
        var quantity = 1

        binding.layoutAddToCart.bind(product, quantity)
        binding.layoutAddToCart.visibility = View.VISIBLE

        binding.layoutAddToCart.setLayoutAddToCartListener(object :
            LayoutAddToCart.LayoutAddToCartListener {
            override fun onMinusBtnClick(product: Product) {
                if (quantity > 1) {
                    quantity--
                    // Update binding
                    binding.layoutAddToCart.bind(product, quantity)
                }
            }

            override fun onAddBtnClick(product: Product) {
                quantity++
                // Update binding
                binding.layoutAddToCart.bind(product, quantity)
            }

            override fun onAddToCartBtnClick(product: Product) {
                var cartContent = viewModel.cartContentNewInstance(product, quantity)
                viewModel.cartRepository.add(cartContent)
                binding.layoutAddToCart.isVisible = false
                Toast.makeText(context, "Item successfully added to cart.", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    /**
     * Private Methods
     * */

    private fun showHomeProducts() {
        viewModel.homeContentsLiveData.observe(viewLifecycleOwner) { it ->
            it.let {
                adapter?.update(it)
                // Show loading
                binding.pbLoadingSpinner.visibility = View.VISIBLE
                // TODO: Set timeout progressbar
                // Hide loading
                binding.pbLoadingSpinner.visibility = View.GONE
            }
        }
    }

    private fun setFilterToolbar() {
        val toolbar: Toolbar = binding.layoutFilter.toolbarFilter

        drawer = binding.drawerLayout
        toggle = ActionBarDrawerToggle(
            activity,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)

        toolbar.setNavigationOnClickListener {
            drawer.openDrawer(GravityCompat.END)
        }

        binding.navViewFilter.setNavigationItemSelectedListener { menuItem ->
            onMenuItemSelected(menuItem)
            true
        }

        onBackBtnClicked()
    }

    private fun onBackBtnClicked() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (drawer.isDrawerOpen(GravityCompat.END)) {
                        drawer.closeDrawer(GravityCompat.END)
                    } else {
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }
            })
    }

    private fun onMenuItemSelected(menuItem: MenuItem) {
        menuItem.isChecked = true
        when(menuItem.itemId) {
            R.id.itemLowToHigh -> {
                adapter?.setSortedPrice(SORT_LOW_TO_HIGH)
            }
            R.id.itemHighToLow -> {
                adapter?.setSortedPrice(SORT_HIGH_TO_LOW)
            }
        }
        drawer.closeDrawer(GravityCompat.END)
    }
}
