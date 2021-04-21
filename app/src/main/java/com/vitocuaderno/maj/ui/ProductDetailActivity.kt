package com.vitocuaderno.maj.ui

import android.os.Bundle
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
    }
}