package com.vitocuaderno.maj.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vitocuaderno.maj.R
import com.vitocuaderno.maj.data.model.CartContent
import com.vitocuaderno.maj.data.model.Product
import com.vitocuaderno.maj.data.util.CurrencyUtil
import com.vitocuaderno.maj.databinding.ItemCartContentBinding

class CartContentsAdapter(
    private val dataSet: MutableList<CartContent>,
    private val cartContentsAdapterListener: CartContentsAdapterListener? = null
) : RecyclerView.Adapter<CartContentsAdapter.CartContentViewHolder>() {

    class CartContentViewHolder(
        private val binding: ItemCartContentBinding,
        private val cartContentsAdapterListener: CartContentsAdapterListener? = null
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(content: CartContent) {
            var picasso = Picasso.get()
            picasso.load(content.productImgUrl).placeholder(R.color.colorSecondary)
                .error(R.drawable.ic_soft_drink).into(binding.imgProduct)
            binding.txtProductName.text = content.productName
            binding.txtUnitCost.text = CurrencyUtil.format(content.productUnitCost)
            binding.txtProductQty.text = content.quantity.toString()

            binding.imgProduct.setOnClickListener {
                cartContentsAdapterListener?.onItemClick(content)
            }

            binding.btnDeleteCartItem.setOnClickListener {
                cartContentsAdapterListener?.onDeleteBtnClick(content)
            }

            binding.btnMinus.setOnClickListener {
                cartContentsAdapterListener?.onMinusBtnClick(content)
            }

            binding.btnAdd.setOnClickListener {
                cartContentsAdapterListener?.onAddBtnClick(content)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartContentViewHolder {
        val content =
            ItemCartContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartContentViewHolder(content, cartContentsAdapterListener)
    }

    override fun onBindViewHolder(holder: CartContentViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    interface CartContentsAdapterListener {
        /**
         * Cart content click listener
         * @param cartContent
         * */
        fun onItemClick(cartContent: CartContent)

        /**
         * Remove cart content click listener
         * @param cartContent
         * */
        fun onDeleteBtnClick(cartContent: CartContent)

        /**
         * Quantity decrement counter
         * @param cartContent
         * */
        fun onMinusBtnClick(cartContent: CartContent)

        /**
         * Quantity increment counter
         * @param cartContent
         * */
        fun onAddBtnClick(cartContent: CartContent)
    }
}