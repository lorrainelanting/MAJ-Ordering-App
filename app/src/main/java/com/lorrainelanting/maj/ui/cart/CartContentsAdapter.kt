package com.lorrainelanting.maj.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.CartContent
import com.lorrainelanting.maj.data.model.Product
import com.lorrainelanting.maj.data.util.CurrencyUtil
import com.lorrainelanting.maj.databinding.ItemCartContentBinding

class CartContentsAdapter(
    private var dataSet: List<Content>,
    private val cartContentsAdapterListener: CartContentsAdapterListener? = null
) : RecyclerView.Adapter<CartContentsAdapter.CartContentViewHolder>() {

    class CartContentViewHolder(
        private val binding: ItemCartContentBinding,
        private val cartContentsAdapterListener: CartContentsAdapterListener? = null
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(content: Content) {
            val picasso = Picasso.get()

            picasso.load(content.product.imgUrl).placeholder(R.color.colorSecondary)
                .error(R.drawable.ic_soft_drink).into(binding.imgProduct)
            binding.txtProductName.text = content.product.name
            binding.txtUnitCost.text = CurrencyUtil.format(content.product.price)
            binding.txtProductQty.text = content.cartContent.quantity.toString()

            binding.imgProduct.setOnClickListener {
                cartContentsAdapterListener?.onItemClick(content.cartContent)
            }

            binding.btnDeleteCartItem.setOnClickListener {
                cartContentsAdapterListener?.onDeleteBtnClick(content.cartContent)
            }

            binding.btnMinus.setOnClickListener {
                cartContentsAdapterListener?.onMinusBtnClick(content.cartContent)
            }

            binding.btnAdd.setOnClickListener {
                cartContentsAdapterListener?.onAddBtnClick(content.cartContent)
            }
        }
    }

    class Content(
        val cartContent: CartContent,
        val product: Product
    )

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

    fun update(dataSet: List<Content>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }
}