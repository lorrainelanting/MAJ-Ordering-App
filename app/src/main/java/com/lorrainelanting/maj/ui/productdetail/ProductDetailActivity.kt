package com.lorrainelanting.maj.ui.productdetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.Product
import com.lorrainelanting.maj.data.util.CurrencyUtil
import com.lorrainelanting.maj.databinding.ActivityProductDetailBinding
import com.lorrainelanting.maj.ui.BaseActivity
import com.lorrainelanting.maj.ui.common.LayoutAddToCart

class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>() {
    private val viewModel = ProductDetailViewModel()

    override fun getLayoutId(): Int = R.layout.activity_product_detail

    companion object {
        const val ID = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.injectProduct(this)
        viewModel.injectCart(this)
        intent?.extras?.let {
            viewModel.productLiveData = viewModel.repository.getItem(it.getInt(ID))
            viewModel.productLiveData.observe(this) { it ->
                bindData(it)
            }
        }
    }

    private fun bindData(product: Product) {
        //  Load image from url string
        val picasso = Picasso.get()
        picasso.load(product.imgUrl).error(R.drawable.ic_homepage).into(
            binding.imgProduct,
            object : Callback {
                override fun onSuccess() {}
                override fun onError(e: Exception?) {
                    e?.printStackTrace()
                }
            }
        )

        binding.txtProductName.text = product.description
        binding.txtUnitCost.text = CurrencyUtil.format(product.unitCost)
        binding.txtProductDescription.text = product.description
        binding.txtProductPackQty.text = product.packQty

        binding.btnAddToCart.setOnClickListener {
            var quantity = 1

            binding.layoutAddToCart.bind(product, quantity)
            binding.layoutAddToCart.visibility = View.VISIBLE

            binding.layoutAddToCart.setLayoutAddToCartListener(object:
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
                    Toast.makeText(this@ProductDetailActivity, "Item successfully added to cart.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}