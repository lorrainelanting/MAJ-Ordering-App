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
    private val ordersContentAdapterCalculation: OrdersContentAdapterCalculation,
    private val ordersContentAdapterListener: OrdersContentAdapterListener? = null
) : RecyclerView.Adapter<OrdersContentAdapter.OrdersContentViewHolder>() {

    private var orderType = COMPLETED_ORDERS

    companion object {
        const val ACTIVE_ORDERS = 0
        const val COMPLETED_ORDERS = 1
    }

    class OrdersContentViewHolder(
        private val binding: ItemOrdersContentBinding,
        private val ordersContentAdapterCalculation: OrdersContentAdapterCalculation,
        private val ordersContentAdapterListener: OrdersContentAdapterListener? = null
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            val picasso = Picasso.get()
            picasso.load(order.productImgUrl).placeholder(R.color.colorSecondary)
                .error(R.drawable.ic_soft_drink).into(binding.imgProduct)

            binding.txtProductDescription.text = order.productName
            binding.txtValUnitCost.text = CurrencyUtil.format(order.productPrice)
            binding.txtResultQuantity.text = order.quantity.toString()

            binding.txtResultTotal.text =
                ordersContentAdapterCalculation.getTotal(order.productPrice, order.quantity)
                    .let { total ->
                        CurrencyUtil.format(total)
                    }

            if (order.status == Constants.STATUS_PLACED_ORDER) {
                binding.btnReorder.visibility = View.GONE
                binding.btnMoveToCompleted.visibility = View.VISIBLE
            } else {
                binding.btnReorder.visibility = View.VISIBLE
                binding.btnMoveToCompleted.visibility = View.GONE
            }

            binding.btnReorder.setOnClickListener {
                ordersContentAdapterListener?.onReorderBtnClick(order)
            }

            binding.btnMoveToCompleted.setOnClickListener {
                ordersContentAdapterListener?.onMoveToCompletedBtnClick(order)
            }
            bindOrderStatus(order)
        }

        private fun bindOrderStatus(order: Order) {
            if (order.status == Constants.STATUS_DELIVERED || order.status == Constants.STATUS_PICKED_UP) {
                binding.txtOrderStatus.visibility = View.VISIBLE
                when(order.deliveryOption) {
                    Constants.OPTION_DELIVER -> {
                        binding.txtOrderStatus.text = Constants.STATUS_DELIVERED
                    }
                    Constants.OPTION_PICK_UP -> {
                        binding.txtOrderStatus.text = Constants.STATUS_PICKED_UP
                    }
                }
            }
            if (order.status == Constants.STATUS_PLACED_ORDER) {
                binding.txtOrderStatus.visibility = View.GONE
            }
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
        return OrdersContentViewHolder(content, ordersContentAdapterCalculation, ordersContentAdapterListener)
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

    interface OrdersContentAdapterListener {
        fun onMoveToCompletedBtnClick(order: Order)

        fun onReorderBtnClick(order: Order)
    }
}