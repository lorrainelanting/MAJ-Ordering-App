package com.lorrainelanting.maj.ui.productdetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.Product
import com.lorrainelanting.maj.data.util.CurrencyUtil
import com.lorrainelanting.maj.databinding.ActivityProductDetailBinding
import com.lorrainelanting.maj.ui.base.BaseActivity
import com.lorrainelanting.maj.ui.common.LayoutAddToCart
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>() {
    private val viewModel = ProductDetailViewModel()

    override fun getLayoutId(): Int = R.layout.activity_product_detail

    companion object {
        const val ID = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initializedRepositories(this)

        intent?.extras?.let {
            viewModel.getProductLiveData(it.getString(ID, "") ?: "").observe(this) { product ->
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

                binding.txtProductName.text = product.name
                binding.txtUnitCost.text = CurrencyUtil.format(product.price)
                binding.txtProductPackQty.text = product.packQty

                binding.btnAddToCart.setOnClickListener {
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
                            viewModel.insertProductToCart(cartContent)
                            binding.layoutAddToCart.isVisible = false
                            Toast.makeText(
                                this@ProductDetailActivity,
                                "Item successfully added to cart.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                }
            }
        }
    }
}