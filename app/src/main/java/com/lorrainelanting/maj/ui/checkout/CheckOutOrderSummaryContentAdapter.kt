package com.lorrainelanting.maj.ui.order

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.CartContent
import com.lorrainelanting.maj.data.util.CurrencyUtil
import com.lorrainelanting.maj.databinding.ItemCheckoutOrderSummaryBinding

class OrderAdapter(
    private var dataSet: List<CartContent>,
    private val orderAdapterCalculation: OrderAdapterCalculation? = null
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    class OrderViewHolder(
        private val binding: ItemCheckoutOrderSummaryBinding,
        private val orderAdapterCalculation: OrderAdapterCalculation? = null
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(content: CartContent) {
            var picasso = Picasso.get()
            picasso.load(content.productImgUrl).placeholder(R.color.colorSecondary)
                .error(R.drawable.ic_soft_drink).into(binding.imgProduct)
            binding.txtProductDescription.text = "${content.productName}  ${content.productPackQty}"
            binding.txtValUnitCost.text = CurrencyUtil.format(content.productUnitCost)
            binding.txtResultQuantity.text = content.quantity.toString()
            orderAdapterCalculation?.getTotal(content.productUnitCost, content.quantity)
                ?.let { total ->
                    binding.txtResultTotal.text = CurrencyUtil.format(total)
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val order = ItemCheckoutOrderSummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(
            order,
            orderAdapterCalculation
        )
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = dataSet[position]
        holder.bind(order)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun update(dataSet: List<CartContent>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    interface OrderAdapterCalculation {
        fun getTotal(unitPrice: Double, quantity: Int): Double
    }
}