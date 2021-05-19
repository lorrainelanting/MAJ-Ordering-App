package com.lorrainelanting.maj.ui.cart

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.CartContent
import com.lorrainelanting.maj.data.util.CurrencyUtil
import com.lorrainelanting.maj.databinding.FragmentCartBinding
import com.lorrainelanting.maj.ui.BaseFragment
import com.lorrainelanting.maj.ui.checkout.CheckOutActivity

class CartFragment : BaseFragment<FragmentCartBinding>(),
    CartContentsAdapter.CartContentsAdapterListener {
    var adapter: CartContentsAdapter? = null

    private val viewModel = CartViewModel()
    private var cartFragmentListener: CartFragmentListener? = null

    override fun getLayoutId(): Int = R.layout.fragment_cart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CartContentsAdapter(emptyList(), this)
        binding.rvCartContents.adapter = adapter
        viewModel.cartContentsLiveData.observe(viewLifecycleOwner) { it ->
            it.let {
                adapter?.update(it)
                //  Empty cart message
                if (it.size === 0) {
                    binding.txtEmptyCart.visibility = View.VISIBLE
                    binding.btnContinueShopping.visibility = View.VISIBLE
                    binding.frameSummary.visibility = View.GONE
                    binding.frameDivider.visibility = View.GONE
                } else {
                    binding.txtEmptyCart.visibility = View.GONE
                    binding.btnContinueShopping.visibility = View.GONE
                    binding.frameSummary.visibility = View.VISIBLE
                    binding.frameDivider.visibility = View.VISIBLE
                }
                binding.txtResultTotalAmt.text = CurrencyUtil.format(computeTotal(it))
            }
        }

        binding.btnContinueShopping.setOnClickListener {
            cartFragmentListener?.onContinueShoppingClick()
        }

        binding.btnCheckout.setOnClickListener() {
            onCheckOutBtnClick()
        }
    }

    private fun computeTotal(list: List<CartContent>): Double {
        var total = 0.00
        for (item in list) {
            val subTotal = item.productUnitCost * item.quantity
            total += subTotal
        }
        return total
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.injectCart(context)
    }

    override fun onItemClick(cartContent: CartContent) {
//        TODO("Not yet implemented")
    }

    override fun onDeleteBtnClick(cartContent: CartContent) {
        val removeBtnClick = { dialog: DialogInterface, which: Int ->
            viewModel.repository.delete(cartContent.id)
        }

        val cancelBtnClick = { dialog: DialogInterface, which: Int ->
        }

        alert(removeBtnClick, cancelBtnClick)
    }

    override fun onMinusBtnClick(cartContent: CartContent) {
        if (cartContent.quantity > 1) {
            cartContent.quantity--
            viewModel.repository.update(cartContent)
            adapter?.notifyDataSetChanged()
        }
    }

    override fun onAddBtnClick(cartContent: CartContent) {
        adapter?.notifyDataSetChanged()
        cartContent.quantity++
        viewModel.repository.update(cartContent)
    }

    private fun onCheckOutBtnClick() {
        val intent = Intent(context, CheckOutActivity().javaClass)
        context?.startActivity(intent)
    }

    private fun alert(
        removeBtnClick: (DialogInterface, Int) -> Unit,
        cancelBtnClick: (DialogInterface, Int) -> Unit
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.txt_dialog_remove_cart_title)
        builder.setMessage(R.string.txt_dialog_remove_cart_body)
        builder.setPositiveButton(
            R.string.txt_dialog_remove,
            DialogInterface.OnClickListener(function = removeBtnClick)
        )
        builder.setNegativeButton(
            R.string.txt_dialog_cancel,
            DialogInterface.OnClickListener(function = cancelBtnClick)
        )
        val alert = builder.create()
        alert.setCanceledOnTouchOutside(true)
        alert.show()
        val cancelBtn = alert.getButton(DialogInterface.BUTTON_NEGATIVE)
        val removeBtn = alert.getButton(DialogInterface.BUTTON_POSITIVE)
        cancelBtn.setTextColor(Color.parseColor("#183C28"))
        removeBtn.setTextColor(Color.parseColor("#C29813"))
    }

    fun setCartFragmentListener(listener: CartFragmentListener) {
        this.cartFragmentListener = listener
    }

    interface CartFragmentListener {
        fun onContinueShoppingClick()
    }
}