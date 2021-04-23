package com.vitocuaderno.maj.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.vitocuaderno.maj.R
import com.vitocuaderno.maj.data.model.HomeContent
import com.vitocuaderno.maj.data.repository.HomeContentRepository
import com.vitocuaderno.maj.data.util.CurrencyUtil
import com.vitocuaderno.maj.databinding.ActivityProductDetailBinding
import com.vitocuaderno.maj.di.Injection

class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>() {
    lateinit var repository: HomeContentRepository

    lateinit var homeContentLiveData: LiveData<HomeContent>

    override fun getLayoutId(): Int = R.layout.activity_product_detail

    companion object {
        const val ID = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = Injection.provideHomeContentRepository(this)
        intent?.extras?.let {
            homeContentLiveData = repository.getItem(it.getInt(ID))
            homeContentLiveData.observe(this) { it ->
                bindData(it)
            }
        }
        setEventsAddToCartLayout()
    }

    private fun bindData(homeContent: HomeContent) {
        //  Load image from url string
        val picasso = Picasso.get()
        picasso.load(homeContent.productImgUrl).error(R.drawable.ic_homepage).into(
            binding.imgProduct,
            object : Callback {
                override fun onSuccess() {}
                override fun onError(e: Exception?) {
                    e?.printStackTrace()
                }
            }
        )

        binding.txtProductName.text = homeContent.productDescription
        binding.txtUnitCost.text = CurrencyUtil.format(homeContent.productUnitCost)
        binding.txtProductDescription.text = homeContent.productDescription
        binding.txtProductPackQty.text = homeContent.productPackQty

        binding.btnAddToCart.setOnClickListener {
            binding.layoutAddToCart.visibility = View.VISIBLE
        }
    }

    private fun setEventsAddToCartLayout() {
        //        TODO Decrease quantity
        binding.layoutAddToCart.findViewById<FrameLayout>(R.id.btnMinus).setOnClickListener {
            Toast.makeText(this, "Minus Button clicked! TODO: Decrease quantity", Toast.LENGTH_SHORT).show()
        }
        //        TODO Increase quantity
        binding.layoutAddToCart.findViewById<FrameLayout>(R.id.btnAdd).setOnClickListener {
            Toast.makeText(this, "Add Button clicked! TODO: Increase quantity", Toast.LENGTH_SHORT).show()
        }
        //        TODO Item added to cart
        binding.layoutAddToCart.findViewById<Button>(R.id.btnAddToCart).setOnClickListener {
            Toast.makeText(this, "TODO: Item added to cart", Toast.LENGTH_SHORT).show()
        }
    }
}