package com.lorrainelanting.maj.ui.checkout

import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.observe
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.CartContent
import com.lorrainelanting.maj.data.util.CurrencyUtil
import com.lorrainelanting.maj.databinding.ActivityCheckOutBinding
import com.lorrainelanting.maj.ui.BaseActivity
import com.lorrainelanting.maj.ui.OrderAdapter

class CheckOutActivity : BaseActivity<ActivityCheckOutBinding>(),
    OrderAdapter.OrderAdapterCalculation {
    var adapter: OrderAdapter? = null

    private val viewModel = CheckOutViewModel()

    override fun getLayoutId(): Int = R.layout.activity_check_out

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = OrderAdapter(emptyList(), this)
        binding.layoutOrderSummary.rvCheckOutProduct.adapter = adapter
        viewModel.injectCart(this)
        viewModel.cartContentsLiveData.observe(this) { cartContents ->
            adapter?.update(cartContents)

            binding.layoutSummaryFees.txtResultSubTotal.text =
                CurrencyUtil.format(computeSubtotal(cartContents))
            binding.frameSummaryCheckout.findViewById<TextView>(R.id.txtResultTotalPayment).text =
                CurrencyUtil.format(computeTotalPayment(computeSubtotal(cartContents), 0.00))
        }
    }

    override fun getTotal(unitPrice: Double, quantity: Int): Double {
        return unitPrice * quantity
    }

    private fun computeSubtotal(list: List<CartContent>): Double {
        var subtotal = 0.00
        for (item in list) {
            val total = item.productUnitCost * item.quantity
            subtotal += total
        }
        return subtotal
    }

    private fun computeTotalPayment(subtotal: Double, shippingFee: Double): Double {
        return subtotal + shippingFee
    }
}