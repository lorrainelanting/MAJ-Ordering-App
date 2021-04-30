package com.vitocuaderno.maj.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.vitocuaderno.maj.R
import com.vitocuaderno.maj.data.model.Product
import com.vitocuaderno.maj.data.repository.product.ProductRepository
import com.vitocuaderno.maj.data.util.CurrencyUtil
import com.vitocuaderno.maj.databinding.ActivityProductDetailBinding
import com.vitocuaderno.maj.di.Injection
import com.vitocuaderno.maj.ui.common.LayoutAddToCart

class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>() {
    lateinit var repository: ProductRepository

    lateinit var productLiveData: LiveData<Product>

    override fun getLayoutId(): Int = R.layout.activity_product_detail

    companion object {
        const val ID = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = Injection.provideProductRepository(this)
        intent?.extras?.let {
            productLiveData = repository.getItem(it.getInt(ID))
            productLiveData.observe(this) { it ->
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
                    //        TODO Item added to cart
                    Toast.makeText(this@ProductDetailActivity, "TODO: Item added to cart", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}