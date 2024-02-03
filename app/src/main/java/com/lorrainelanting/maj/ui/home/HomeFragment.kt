package com.lorrainelanting.maj.ui.home

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
import androidx.fragment.app.viewModels
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.app.MajApplication
import com.lorrainelanting.maj.data.model.Product
import com.lorrainelanting.maj.databinding.FragmentHomeBinding
import com.lorrainelanting.maj.ui.base.BaseActivity
import com.lorrainelanting.maj.ui.base.BaseFragment
import com.lorrainelanting.maj.ui.common.LayoutAddToCart
import com.lorrainelanting.maj.ui.productdetail.ProductDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(),
    HomeProductAdapter.HomeAdapterListener {
    var adapter: HomeProductAdapter? = null

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override val viewModel: HomeViewModel by viewModels()

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
        viewModel.homeContentsLiveData.observe(viewLifecycleOwner) { list ->
            list.let {
                adapter?.update(list)
                showHomeProducts()
                if (list.isEmpty()) {
                    binding.layoutEmptyHome.root.visibility = View.VISIBLE
                } else {
                    binding.layoutEmptyHome.root.visibility = View.GONE
                }
            }
        }

        //Swipe refresh
        binding.swipeRefLayout.setOnRefreshListener {
            val products = viewModel.homeContentsLiveData.value!!
            if (products.isEmpty()) {
                binding.layoutEmptyHome.root.visibility = View.VISIBLE

                if ((activity as BaseActivity<*>).isNetworkAvailable) {
                    fetchRemoteProducts()
                }
            } else {
                binding.layoutEmptyHome.root.visibility = View.GONE
            }
            showHomeProducts()
            binding.swipeRefLayout.isRefreshing = false
        }

        binding.swipeRefLayout.setColorSchemeResources(R.color.colorSecondary)
    }

    private fun fetchRemoteProducts() {
        (activity?.application as MajApplication).fetchRemoteProducts()
    }

    override fun onItemClick(product: Product) {
        val intent = Intent(this.context, ProductDetailActivity().javaClass)
        intent.putExtra(ProductDetailActivity.ID, product.id)
        viewModel.getProductLiveData(product.id)
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
                val cartContent = viewModel.cartContentNewInstance(product, quantity)
                viewModel.insertCartContent(cartContent)
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
        // Show loading
        binding.pbLoadingSpinner.visibility = View.VISIBLE
        // TODO: Set timeout progressbar
        // Hide loading
        binding.pbLoadingSpinner.visibility = View.GONE
    }

//    private fun showEmptyHomeProductsLayout(products: List<Product>) {
//        if (products.isEmpty()) {
//            binding.layoutEmptyHome.root.visibility = View.VISIBLE
//        }
//        if (products.isNotEmpty()) {
//            binding.layoutEmptyHome.root.visibility = View.GONE
//        }
//    }

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
        when (menuItem.itemId) {
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
