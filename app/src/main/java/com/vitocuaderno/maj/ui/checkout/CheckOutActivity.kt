package com.vitocuaderno.maj.ui.checkout

import android.os.Bundle
import androidx.lifecycle.observe
import com.vitocuaderno.maj.R
import com.vitocuaderno.maj.databinding.ActivityCheckOutBinding
import com.vitocuaderno.maj.ui.BaseActivity

class CheckOutActivity : BaseActivity<ActivityCheckOutBinding>() {
    companion object{
        const val ID_ARR = "idArray"
    }

    private val viewModel = CheckOutViewModel()

    override fun getLayoutId(): Int = R.layout.activity_check_out

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.injectCart(this)
        intent?.extras?.let {
            viewModel.cartContentsLiveData.observe(this) { it ->
            }
        }

    }
}