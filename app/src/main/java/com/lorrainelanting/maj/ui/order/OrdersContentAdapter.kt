package com.lorrainelanting.maj.ui.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.Order
import com.lorrainelanting.maj.data.util.Constants
import com.lorrainelanting.maj.data.util.CurrencyUtil
import com.lorrainelanting.maj.databinding.ItemOrdersContentBinding
import com.squareup.picasso.Picasso

class OrdersContentAdapter(
    private var dataset: List<Order>,
    private val ordersContentAdapterCalculation: OrdersContentAdapterCalculation
) : RecyclerView.Adapter<OrdersContentAdapter.OrdersContentViewHolder>() {

    private var orderType = COMPLETED_ORDERS

    class OrdersContentViewHolder(
        private val binding: ItemOrdersContentBinding,
        private val ordersContentAdapterCalculation: OrdersContentAdapterCalculation
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            var picasso = Picasso.get()
            picasso.load(order.productImgUrl).placeholder(R.color.colorSecondary)
                .error(R.drawable.ic_soft_drink).into(binding.imgProduct)

            binding.txtProductDescription.text = order.productName
            binding.txtValUnitCost.text = CurrencyUtil.format(order.productPrice)
            binding.txtResultQuantity.text = order.quantity.toString()

            binding.txtResultTotal.text =
                ordersContentAdapterCalculation?.getTotal(order.productPrice, order.quantity)
                    ?.let { total ->
                        CurrencyUtil.format(total)
                    }

            if (order.status == Constants.STATUS_PLACED_ORDER) {
                binding.btnReorder.visibility = View.GONE
            } else {
                binding.btnReorder.visibility = View.VISIBLE
            }

//            binding.btnReorder.setOnClickListener {
////                TODO:
//            }
        }
    }

    interface OrdersContentAdapterCalculation {
        fun getTotal(unitPrice: Double, quantity: Int): Double
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrdersContentViewHolder {
        val content =
            ItemOrdersContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrdersContentViewHolder(content, ordersContentAdapterCalculation)
    }

    override fun getItemCount(): Int {
        return getFilteredDataSet().size
    }

    override fun onBindViewHolder(holder: OrdersContentViewHolder, position: Int) {
        val item = getFilteredDataSet()[position]
        holder.bind(item)
    }

    fun update(dataSet: List<Order>) {
        this.dataset = dataSet
        notifyDataSetChanged()
    }

    private fun getFilteredDataSet(): List<Order> {
        return when (orderType) {
            ACTIVE_ORDERS -> {
                dataset.filter {
                    it.status == Constants.STATUS_PLACED_ORDER
                }
            }
            COMPLETED_ORDERS -> {
                dataset.filter {
                    it.status == Constants.STATUS_DELIVERED || it.status == Constants.STATUS_PICKED_UP
                }
            }
            else -> {
                dataset
            }
        }
    }

    fun setOrderStatus(status: Int) {
        orderType = status
        notifyDataSetChanged()
    }

    companion object {
        const val ACTIVE_ORDERS = 0
        const val COMPLETED_ORDERS = 1
    }
}