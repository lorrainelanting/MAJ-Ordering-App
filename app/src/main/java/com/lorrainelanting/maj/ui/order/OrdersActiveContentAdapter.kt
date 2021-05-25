package com.lorrainelanting.maj.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.Order
import com.lorrainelanting.maj.data.util.CurrencyUtil
import com.lorrainelanting.maj.databinding.ItemOrdersContentActiveBinding
import com.squareup.picasso.Picasso

class OrdersActiveContentAdapter(
    private var dataSet: List<Order>,
    private val adapterCalculation: AdapterCalculation? = null
) :
    RecyclerView.Adapter<OrdersActiveContentAdapter.OrdersContentViewHolder>() {
    class OrdersContentViewHolder(
        private val binding: ItemOrdersContentActiveBinding,
        private val adapterCalculation: AdapterCalculation? = null
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            var picasso = Picasso.get()
            picasso.load(order.productImgUrl).placeholder(R.color.colorSecondary)
                .error(R.drawable.ic_soft_drink).into(binding.imgProduct)

            binding.txtProductDescription.text = order.productName
            binding.txtValUnitCost.text = CurrencyUtil.format(order.productPrice)
            binding.txtResultQuantity.text = order.quantity.toString()

            binding.txtResultTotal.text =
                adapterCalculation?.getTotal(order.productPrice, order.quantity)?.let { total ->
                    CurrencyUtil.format(total)
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersContentViewHolder {
        val content = ItemOrdersContentActiveBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrdersContentViewHolder(content, adapterCalculation)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: OrdersContentViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item)
    }

    fun update(dataSet: List<Order>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    interface AdapterCalculation {
        fun getTotal(unitPrice: Double, quantity: Int): Double
    }
}