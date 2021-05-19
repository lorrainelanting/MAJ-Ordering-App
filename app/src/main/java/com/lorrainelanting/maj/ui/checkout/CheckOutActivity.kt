package com.lorrainelanting.maj.ui.checkout

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
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

//        Injections
        viewModel.injectUser(this)
        viewModel.injectDeliveryAddress(this)
        viewModel.injectCart(this)

        viewModel.userLiveData.observe(this) { list ->
            for (user in list) {
                binding.layoutCustomerDetail.txtCustomerName.text = user.fullName
                binding.layoutCustomerDetail.txtStoreName.text = user.storeName
                binding.layoutCustomerDetail.txtCustomerMobile.setText(user.contactNum.toString())
            }
        }

        viewModel.deliveryAddressLiveData.observe(this) { list ->
            for (deliveryAddress in list) {
                binding.layoutCustomerDetail.txtCustomerAddress.text =
                    "${deliveryAddress.streetName}, ${deliveryAddress.barangay}, ${deliveryAddress.city}"
            }
        }

        viewModel.cartContentsLiveData.observe(this) { cartContents ->
            adapter?.update(cartContents)

            binding.layoutSummaryFees.txtResultSubTotal.text =
                CurrencyUtil.format(computeSubtotal(cartContents))
            binding.frameSummaryCheckout.findViewById<TextView>(R.id.txtResultTotalPayment).text =
                CurrencyUtil.format(computeTotalPayment(computeSubtotal(cartContents), 0.00))
        }

        binding.layoutCustomerDetail.txtEdit.setOnClickListener {
            setResult(4)
            finish()
        }

        binding.layoutDeliveryOpt.rgDeliveryOption.setOnCheckedChangeListener { group, checkedId ->
            onRadioBtnClicked(group)
        }

        binding.btnPlaceOrder.setOnClickListener {
            val positiveBtnClick = { dialog: DialogInterface, which: Int ->
                setResult(2)
                finish()
            }

            val negativeBtnClick = { dialog: DialogInterface, which: Int ->
            }

            alert(positiveBtnClick, negativeBtnClick)
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

    private fun onRadioBtnClicked(view: View) {
        if (view is RadioGroup) {
            if (view.checkedRadioButtonId != -1) {
                binding.btnPlaceOrder.isEnabled = true
            }
        }
    }

    private fun alert(
        positiveBtnClick: (DialogInterface, Int) -> Unit,
        negativeBtnClick: (DialogInterface, Int) -> Unit
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.txt_dialog_place_order_title)
        builder.setMessage(R.string.txt_dialog_place_order_body)
        builder.setPositiveButton(
            R.string.txt_dialog_place_order_positive,
            DialogInterface.OnClickListener(function = positiveBtnClick)
        )
        builder.setNegativeButton(
            R.string.txt_dialog_place_order_negative,
            DialogInterface.OnClickListener(function = negativeBtnClick)
        )
        val alert = builder.create()
        alert.setCanceledOnTouchOutside(true)
        alert.show()
        val negativeBtn = alert.getButton(DialogInterface.BUTTON_NEGATIVE)
        val positiveBtn = alert.getButton(DialogInterface.BUTTON_POSITIVE)

        negativeBtn.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        positiveBtn.setTextColor(ContextCompat.getColor(this, R.color.colorSecondaryVariant))
    }

    companion object {
        const val SOURCE = "source"

        const val PROFILE_REQ = 1
    }
}