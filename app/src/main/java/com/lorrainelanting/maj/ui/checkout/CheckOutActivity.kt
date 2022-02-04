package com.lorrainelanting.maj.ui.checkout

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.User
import com.lorrainelanting.maj.data.util.Constants
import com.lorrainelanting.maj.data.util.CurrencyUtil
import com.lorrainelanting.maj.data.util.DateUtil
import com.lorrainelanting.maj.databinding.ActivityCheckOutBinding
import com.lorrainelanting.maj.ui.base.BaseActivity
import java.util.*

class CheckOutActivity : BaseActivity<ActivityCheckOutBinding>(),
    CheckOutOrderSummaryContentAdapter.CheckOutOrderSummaryContentAdapterCalculation {

    private lateinit var viewModel: CheckOutViewModel
    private lateinit var delAddress: String
    private lateinit var selectedDeliveryDate: Date
    private lateinit var customerInfo: User
    private var deliveryDate: Long = 0
    var adapter: CheckOutOrderSummaryContentAdapter? = null

    companion object {
        const val MAJ_CONTACT_NUM = "09170000000" // 09178913668
    }

    override fun getLayoutId(): Int = R.layout.activity_check_out

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedDeliveryDate = Date()
        adapter =
            CheckOutOrderSummaryContentAdapter(emptyList(), this)

        binding.layoutOrderSummary.rvCheckOutProduct.adapter = adapter

        viewModel = CheckOutViewModel()
        viewModel.initializedRepositories(this)
        viewModel.usersLiveData.observe(this) { list ->
            if (list.isNotEmpty()) {
                binding.layoutUserBanner.root.visibility = View.GONE
                binding.layoutCustomerDetail.root.visibility = View.VISIBLE
            } else {
                binding.layoutUserBanner.root.visibility = View.VISIBLE
                binding.layoutCustomerDetail.root.visibility = View.GONE

            }

            binding.layoutUserBanner.root.setOnClickListener {
                setResult(4)
                finish()
            }
            binding.layoutUserBanner.txtWarningMessage.setText(
                SpannableString(Html.fromHtml("Please complete your user profile first. <br/><b><u>Update Now</u></b>")),
                TextView.BufferType.SPANNABLE
            )

            for (user in list) {
                if (user.contactNum != "" || user.fullName != "") {
                    binding.layoutUserBanner.root.visibility = View.GONE
                    binding.layoutCustomerDetail.root.visibility = View.VISIBLE
                } else {
                    binding.layoutUserBanner.root.visibility = View.VISIBLE
                    binding.layoutCustomerDetail.root.visibility = View.GONE
                }

                customerInfo = user
                binding.layoutCustomerDetail.txtCustomerName.text = user.fullName
                binding.layoutCustomerDetail.txtStoreName.text = user.storeName
                binding.layoutCustomerDetail.txtCustomerMobile.setText(user.contactNum)

                if (isCustomerDetailsSet(customerInfo.fullName, customerInfo.contactNum)) {
                    binding.btnPlaceOrder.isEnabled = false
                }
            }
            deliveryDate = DateUtil.formatToDate(selectedDeliveryDate)
        }

        viewModel.deliveryAddressesLiveData.observe(this) { list ->
            for (deliveryAddress in list) {
                delAddress =
                    "${deliveryAddress.streetName}, ${deliveryAddress.barangay}, ${deliveryAddress.city}"
                binding.layoutCustomerDetail.txtCustomerAddress.text = delAddress

                if (isDeliveryAddressSet(delAddress)) {
                    binding.btnPlaceOrder.isEnabled = false
                }
            }
        }

        viewModel.cartContentsLiveData.observe(this) { list ->
            val cartContents = mutableListOf<CheckOutOrderSummaryContentAdapter.Content>()
            for (cartContent in list) {
                viewModel.getProduct(cartContent.productId)?.let { product ->
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
                    val optionDeliver = binding.layoutDeliveryDetails.radioBtnDeliver.isChecked
                    val optionPickUp = binding.layoutDeliveryDetails.radioBtnPickup.isChecked
                    var cartContentId: String

                    if (this.isNetworkAvailable) {
                        var deliveryOption = 0
                        val status = Constants.STATUS_PLACED_ORDER

                        if (optionDeliver) {
                            deliveryOption = Constants.OPTION_DELIVER
                        }
                        if (optionPickUp) {
                            deliveryOption = Constants.OPTION_PICK_UP
                        }

                        setOrder(deliveryOption, status)

                        for (cartContent in cartContents) {
                            cartContentId = cartContent.cartContent.id

                            // Remove placed item from cart.
                            viewModel.deleteCartItem(cartContentId)
                        }

                    } else {
                        onNetworkNotAvailable(cartContents)
                    }
                }

                val negativeBtnClick = { dialog: DialogInterface, which: Int ->
                }

                alert(positiveBtnClick, negativeBtnClick)
            }
        }

        binding.layoutCustomerDetail.txtEdit.setOnClickListener {
            setResult(4)
            finish()
        }

        binding.layoutDeliveryDetails.rgDeliveryOption.setOnCheckedChangeListener { group, checkedId ->
            onRadioBtnClicked(group)
        }

        binding.layoutDeliveryDetails.imgEditCalendar.setOnClickListener {
            onSetDateClicked()
        }

        binding.layoutDeliveryDetails.txtSelectDate.setOnClickListener {
            onSetDateClicked()
        }
    }

    private fun showSelectedDate() {
        val viewSelectedDate = binding.layoutDeliveryDetails.txtSelectedDate
        val txtSelectDate = binding.layoutDeliveryDetails.txtSelectDate
        val txtLabelSelectedDate = binding.layoutDeliveryDetails.txtLabelSelectedDate

        if (viewSelectedDate.text != "") {
            txtLabelSelectedDate.visibility = View.VISIBLE
            txtSelectDate.visibility = View.GONE
        } else {
            txtLabelSelectedDate.visibility = View.GONE
            txtSelectDate.visibility = View.VISIBLE
        }
    }

    private fun onSetDateClicked() {
        val viewSelectedDate = binding.layoutDeliveryDetails.txtSelectedDate
        val cal = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // bind selected date
                viewSelectedDate.text = DateUtil.formatToString(cal.time)
                selectedDeliveryDate = cal.time
                showSelectedDate()
            }

        val datePickerDialog = DatePickerDialog(
            this, dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )

        // Set minimum date to current date
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()

        datePickerDialog.show()
    }

    override fun getTotal(unitPrice: Double, quantity: Int): Double {
        return unitPrice * quantity
    }

    private fun computeSubtotal(list: List<CheckOutOrderSummaryContentAdapter.Content>): Double {
        var subtotal = 0.00
        for (item in list) {
            val total = item.product.price * item.cartContent.quantity
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

    private fun setOrder(
        deliveryOption: Int,
        status: String,
    ) {
        viewModel.insertOrder(
            deliveryOption,
            status,
            deliveryDate
        )
        setResult(2)
        finish()
    }

    private fun onNetworkNotAvailable(
        contents: List<CheckOutOrderSummaryContentAdapter.Content>,
    ) {

        val selectedDeliveryOption =
            binding.layoutDeliveryDetails.rgDeliveryOption.checkedRadioButtonId
        val selectedDeliveryText = findViewById<RadioButton>(selectedDeliveryOption).text.toString()
        var smsOrderDetails = ""

        // Loop orders
        for (content in contents) {
            smsOrderDetails += getString(
                R.string.sms_order_detail,
                content.cartContent.quantity.toString(),
                content.product.name
            ) + "\n"
        }

        val smsFullMessage = getString(
            R.string.sms_order, smsOrderDetails,
            selectedDeliveryText,
            DateUtil.formatToString(selectedDeliveryDate),
            customerInfo.fullName,
            customerInfo.contactNum,
            delAddress
        )

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            type = "text/plain"
            data = Uri.parse("smsto: $MAJ_CONTACT_NUM")
            putExtra(Intent.EXTRA_TEXT, smsFullMessage)
        }

        if (intent.resolveActivity(this.packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun isCustomerDetailsSet(name: String, contactNum: String): Boolean {
        return !(name.isEmpty() && contactNum.isEmpty())
    }

    private fun isDeliveryAddressSet(address: String): Boolean {
        return address.isNotEmpty()
    }
}