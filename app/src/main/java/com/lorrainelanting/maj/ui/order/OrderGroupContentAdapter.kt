package com.lorrainelanting.maj.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.Order
import com.lorrainelanting.maj.data.model.OrderGroup
import com.lorrainelanting.maj.data.util.Constants
import com.lorrainelanting.maj.data.util.CurrencyUtil
import com.lorrainelanting.maj.databinding.ItemOrderGroupContentBinding
import com.squareup.picasso.Picasso

class OrderGroupContentAdapter(
    private var dataset: List<Content>,
    private val orderGroupContentAdapterCalculation: OrderGroupContentAdapterCalculation
) : RecyclerView.Adapter<OrderGroupContentAdapter.OrderGroupContentViewHolder>() {

    private var orderType = COMPLETED_ORDERS

    companion object {
        const val ACTIVE_ORDERS = 0
        const val COMPLETED_ORDERS = 1
    }

    interface OrderGroupContentAdapterCalculation {
        fun getTotal(productPrice: Double, quantity: Int): Double
    }

    class Content(
        val order: Order,
        val orderGroup: OrderGroup
    )

    class OrderGroupContentViewHolder(
        private val binding: ItemOrderGroupContentBinding,
        private val orderGroupContentAdapterCalculation: OrderGroupContentAdapterCalculation
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(content: Content) {
            val order = content.order
            val picasso = Picasso.get()

            picasso.load(order.productImgUrl).placeholder(R.color.colorSecondary)
                .error(R.drawable.ic_soft_drink).into(binding.imgProduct)

            binding.txtProductDescription.text = order.productName
            binding.txtValUnitCost.text = CurrencyUtil.format(order.productPrice)
            binding.txtResultQuantity.text = order.quantity.toString()
            binding.txtResultTotal.text =
                orderGroupContentAdapterCalculation.getTotal(order.productPrice, order.quantity)
                    .let { total ->
                        CurrencyUtil.format(total)
                    }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderGroupContentViewHolder {
        val content = ItemOrderGroupContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderGroupContentViewHolder(content, orderGroupContentAdapterCalculation)
    }

    override fun getItemCount(): Int {
        return getFilteredDataSet().size
    }

    override fun onBindViewHolder(holder: OrderGroupContentViewHolder, position: Int) {
        val item = getFilteredDataSet()[position]
        holder.bind(item)
    }

    fun update(dataSet: List<Content>) {
        this.dataset = dataSet
        notifyDataSetChanged()
    }

    fun setOrderStatus(status: Int) {
        orderType = status
        notifyDataSetChanged()
    }

    private fun getFilteredDataSet(): List<Content> {
        for (data in dataset) {
            if (data.order.orderGroupId == data.orderGroup.id) {
                when (orderType) {
                    OrdersContentAdapter.ACTIVE_ORDERS -> {
                        dataset.filter {
                            it.orderGroup.status == Constants.STATUS_PLACED_ORDER
                        }
                    }
                    OrdersContentAdapter.COMPLETED_ORDERS -> {
                        dataset.filter {
                            it.orderGroup.status == Constants.STATUS_DELIVERED || it.orderGroup.status == Constants.STATUS_PICKED_UP
                        }
                    }
                    else -> {
                        dataset
                    }
                }
            }
        }
        return dataset
    }
}