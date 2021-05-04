package com.vitocuaderno.maj.ui.home

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import com.vitocuaderno.maj.R
import com.vitocuaderno.maj.data.model.CartContent
import com.vitocuaderno.maj.data.model.Product
import com.vitocuaderno.maj.data.repository.cart.CartRepository
import com.vitocuaderno.maj.data.repository.product.ProductRepository
import com.vitocuaderno.maj.databinding.FragmentHomeBinding
import com.vitocuaderno.maj.di.Injection
import com.vitocuaderno.maj.ui.BaseFragment
import com.vitocuaderno.maj.ui.ProductDetailActivity
import com.vitocuaderno.maj.ui.common.LayoutAddToCart

class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeProductAdapter.HomeAdapterListener {
    lateinit var repository: ProductRepository
    lateinit var cartRepository: CartRepository

    var adapter: HomeProductAdapter? = null
    var homeContents = mutableListOf<Product>()

    private val homeContentsLiveData by lazy {
        repository.getList()
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = HomeProductAdapter(homeContents, this)
        binding.rvHomeContents.adapter = adapter

        showHomeProducts()

        //Swipe refresh
        binding.swipeRefLayout.setOnRefreshListener {
            showHomeProducts()

            binding.swipeRefLayout.isRefreshing = false
        }
        binding.swipeRefLayout.setColorSchemeColors(Color.GREEN)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        repository = Injection.provideProductRepository(context)
        cartRepository = Injection.provideCartRepository(context)
    }

    override fun onItemClick(product: Product) {
        val intent = Intent(this.context, ProductDetailActivity().javaClass)
        intent.putExtra(ProductDetailActivity.ID, product.id)
        repository.getItem(product.id)
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
                var cartContent: CartContent = CartContent.newInstance(
                    productId = product.id,
                    productName = product.name,
                    productImgUrl = product.imgUrl,
                    productUnitCost = product.unitCost,
                    quantity = quantity
                )
                cartRepository.add(cartContent)
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
        homeContentsLiveData.observe(viewLifecycleOwner) { it ->
            it.let {
                homeContents.clear()
                homeContents.addAll(it)
                //        TODO: Hide loading
                adapter?.notifyDataSetChanged()
            }
        }
    }
}
