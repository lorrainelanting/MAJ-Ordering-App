package com.lorrainelanting.maj.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.Order
import com.lorrainelanting.maj.data.util.CurrencyUtil
import com.lorrainelanting.maj.databinding.ItemOrdersContentPastBinding
import com.squareup.picasso.Picasso

class OrdersPastContentAdapter(
    private var dataset: List<Order>,
    private val pastContentAdapterCalculation: PastContentAdapterCalculation
) : RecyclerView.Adapter<OrdersPastContentAdapter.OrdersPastContentViewHolder>() {

    class OrdersPastContentViewHolder(
        private val binding: ItemOrdersContentPastBinding,
        private val pastContentAdapterCalculation: PastContentAdapterCalculation
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            var picasso = Picasso.get()
            picasso.load(order.productImgUrl).placeholder(R.color.colorSecondary)
                .error(R.drawable.ic_soft_drink).into(binding.imgProduct)

            binding.txtProductDescription.text = order.productName
            binding.txtValUnitCost.text = CurrencyUtil.format(order.productPrice)
            binding.txtResultQuantity.text = order.quantity.toString()

            binding.txtResultTotal.text =
                pastContentAdapterCalculation?.getTotal(order.productPrice, order.quantity)?.let { total ->
                    CurrencyUtil.format(total)
                }

            binding.btnReorder.setOnClickListener {
//                TODO:
            }
        }
    }

    interface PastContentAdapterCalculation {
        fun getTotal(unitPrice: Double, quantity: Int): Double
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrdersPastContentViewHolder {
        val content = ItemOrdersContentPastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrdersPastContentViewHolder(content, pastContentAdapterCalculation)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: OrdersPastContentViewHolder, position: Int) {
        val item = dataset[position]
        holder.bind(item)
    }

    fun update(dataSet: List<Order>) {
        this.dataset = dataSet
        notifyDataSetChanged()
    }
}