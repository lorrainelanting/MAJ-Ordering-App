package com.lorrainelanting.maj.ui.checkout

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.observe
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.Order
import com.lorrainelanting.maj.data.model.User
import com.lorrainelanting.maj.data.util.CurrencyUtil
import com.lorrainelanting.maj.databinding.ActivityCheckOutBinding
import com.lorrainelanting.maj.di.Injection
import com.lorrainelanting.maj.ui.base.BaseActivity


class CheckOutActivity : BaseActivity<ActivityCheckOutBinding>(),
    CheckOutOrderSummaryContentAdapter.CheckOutOrderSummaryContentAdapterCalculation {
    var adapter: CheckOutOrderSummaryContentAdapter? = null

    lateinit var viewModel: CheckOutViewModel
    lateinit var customerInfo: User
    lateinit var delAddress: String


    companion object {
        const val SOURCE = "source"

        const val PROFILE_REQ = 1

//        val URI = Uri.parse("fb-messenger://user/majplacelipa")
    }

    override fun getLayoutId(): Int = R.layout.activity_check_out

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter =
            CheckOutOrderSummaryContentAdapter(emptyList(), this)
        binding.layoutOrderSummary.rvCheckOutProduct.adapter = adapter

        viewModel = CheckOutViewModel(
            Injection.provideCartRepository(this),
            Injection.provideProductRepository(this),
            Injection.provideUserRepository(this),
            Injection.provideDeliveryAddressRepository(this),
            Injection.provideOrderRepository(this)
        )

        viewModel.userLiveData.observe(this) { list ->
            for (user in list) {
                customerInfo = user
                binding.layoutCustomerDetail.txtCustomerName.text = user.fullName
                binding.layoutCustomerDetail.txtStoreName.text = user.storeName
                binding.layoutCustomerDetail.txtCustomerMobile.setText(user.contactNum.toString())
            }
        }

        viewModel.deliveryAddressLiveData.observe(this) { list ->
            for (deliveryAddress in list) {
                delAddress = "${deliveryAddress.streetName}, ${deliveryAddress.barangay}, ${deliveryAddress.city}"
                binding.layoutCustomerDetail.txtCustomerAddress.text = delAddress

            }
        }

        viewModel.cartContentsLiveData.observe(this) { list ->
            list.let {
                val cartContents = mutableListOf<CheckOutOrderSummaryContentAdapter.Content>()
                for (cartContent in list) {
                    viewModel.productRepository.get(cartContent.productId)?.let { product ->
                        cartContents.add(
                            CheckOutOrderSummaryContentAdapter.Content(
                                cartContent,
                                product
                            )
                        )
                    }
                }

                adapter?.update(cartContents)

                binding.layoutSummaryFees.txtResultSubTotal.text =
                    CurrencyUtil.format(computeSubtotal(cartContents))
                binding.frameSummaryCheckout.findViewById<TextView>(R.id.txtResultTotalPayment).text =
                    CurrencyUtil.format(computeTotalPayment(computeSubtotal(cartContents), 0.00))

                binding.btnPlaceOrder.setOnClickListener {
                    val positiveBtnClick = { dialog: DialogInterface, which: Int ->
                        for (cartContent in cartContents) {
                            var customerDetails = getString(R.string.customer_details, customerInfo.fullName, delAddress, customerInfo.contactNum.toString())
                            val orderDetails = getString(R.string.order_details, cartContent.product.name, cartContent.cartContent.quantity.toString(), "30-May-2021", "0.00", "100.00")

                            val uri = Uri.parse("https://www.facebook.com/majplacelipa/")
                            val page = Uri.parse("fb://page/104399324329005")
//                            106709074945805 page.debugger

                            val page1 = Uri.parse("fb://user/page.debugger")
                            val intent = Intent(Intent.ACTION_SEND, page)
                                .setType("text/plain")
                                .setPackage("com.facebook.orca")
                                .putExtra(Intent.EXTRA_TEXT, orderDetails)

                            if (intent.resolveActivity(this.packageManager) != null) {
                                startActivityForResult(intent, 2)

                                val order: Order = viewModel.ordersContentNewInstance("1", cartContent)
                                viewModel.orderRepository.save(order)

                                val order1: Order = viewModel.ordersContentNewInstance("2", cartContent)
                                viewModel.orderRepository.save(order1)

                                setResult(2)
                                finish()
                            } else {
                                Toast.makeText(this, "No app found", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    val negativeBtnClick = { dialog: DialogInterface, which: Int ->
                    }

                    alert(positiveBtnClick, negativeBtnClick)
                }

            }
        }

        binding.layoutCustomerDetail.txtEdit.setOnClickListener {
            setResult(4)
            finish()
        }

        binding.layoutDeliveryOpt.rgDeliveryOption.setOnCheckedChangeListener { group, checkedId ->
            onRadioBtnClicked(group)
        }
    }

    override fun getTotal(unitPrice: Double, quantity: Int): Double {
        return unitPrice * quantity
    }

    private fun computeSubtotal(list: List<CheckOutOrderSummaryContentAdapter.Content>): Double {
        var subtotal = 0.00
        for (item in list) {
            val total = 658.00 * item.cartContent.quantity
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
}