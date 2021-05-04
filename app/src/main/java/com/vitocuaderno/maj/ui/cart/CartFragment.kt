package com.vitocuaderno.maj.ui.cart

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.vitocuaderno.maj.R
import com.vitocuaderno.maj.data.model.CartContent
import com.vitocuaderno.maj.data.repository.cart.CartRepository
import com.vitocuaderno.maj.databinding.FragmentCartBinding
import com.vitocuaderno.maj.databinding.FragmentHomeBinding
import com.vitocuaderno.maj.di.Injection
import com.vitocuaderno.maj.ui.BaseFragment
import com.vitocuaderno.maj.ui.MainActivity
import com.vitocuaderno.maj.ui.ProductDetailActivity
import com.vitocuaderno.maj.ui.home.HomeFragment

class CartFragment : BaseFragment<FragmentCartBinding>(),
    CartContentsAdapter.CartContentsAdapterListener {
    lateinit var repository: CartRepository
    lateinit var navController: NavController
    var adapter: CartContentsAdapter? = null
    var cartContents = mutableListOf<CartContent>()

    private val cartContentsLiveData by lazy {
        repository.getList()
    }

    private var cartFragmentListener: CartFragmentListener? = null

    override fun getLayoutId(): Int = R.layout.fragment_cart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CartContentsAdapter(cartContents, this)
        binding.rvCartContents.adapter = adapter
        cartContentsLiveData.observe(viewLifecycleOwner) { it ->
            it.let {
                cartContents.clear()
                //        TODO: Show loading
                cartContents.addAll(it)
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

                //        TODO: Hide loading
                adapter?.notifyDataSetChanged()
            }
        }

        binding.btnContinueShopping.setOnClickListener {
            cartFragmentListener?.onContinueShoppingClick()
        }

        binding.btnCheckout.setOnClickListener {
            onCheckOutBtnClick()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        repository = Injection.provideCartRepository(context)
    }

    override fun onItemClick(cartContent: CartContent) {
//        TODO("Not yet implemented")
    }

    override fun onDeleteBtnClick(cartContent: CartContent) {
        val removeBtnClick = { dialog: DialogInterface, which: Int ->
            repository.delete(cartContent.id)
        }

        val cancelBtnClick = { dialog: DialogInterface, which: Int ->
        }

        alert(removeBtnClick, cancelBtnClick)
    }

    override fun onMinusBtnClick(cartContent: CartContent) {
        if (cartContent.quantity > 1) {
            cartContent.quantity--
            adapter?.notifyDataSetChanged()
        }
    }

    override fun onAddBtnClick(cartContent: CartContent) {
        cartContent.quantity++
        adapter?.notifyDataSetChanged()
    }

    private fun onCheckOutBtnClick() {
        Toast.makeText(this.context, "TODO: Checkout items", Toast.LENGTH_SHORT).show()
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