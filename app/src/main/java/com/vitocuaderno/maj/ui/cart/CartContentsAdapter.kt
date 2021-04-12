package com.vitocuaderno.maj.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vitocuaderno.maj.data.model.CartContent
import com.vitocuaderno.maj.data.util.CurrencyUtil
import com.vitocuaderno.maj.databinding.ItemCartContentBinding

class CartContentsAdapter(
    private val dataSet: MutableList<CartContent>,
) : RecyclerView.Adapter<CartContentsAdapter.CartContentViewHolder>() {

    class CartContentViewHolder(private val binding: ItemCartContentBinding) : RecyclerView.ViewHolder(
        binding.root) {
        fun bind(content: CartContent) {
//                binding.imgProduct TODO: Glide library or Picasso library. Load image from url string

            binding.txtProductName.text = content.productName
            binding.txtUnitCost.text = CurrencyUtil.format(content.productUnitCost)
            binding.etQuantity.setText(content.quantity.toString())
            binding.btnDeleteCartItem.setOnClickListener {
                // TODO create listener flow. eg. listener.onClickBtnDelete(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartContentViewHolder {
        val content = ItemCartContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartContentViewHolder(content)
    }

    override fun onBindViewHolder(holder: CartContentViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}