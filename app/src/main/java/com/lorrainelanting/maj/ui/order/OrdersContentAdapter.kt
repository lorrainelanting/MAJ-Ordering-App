package com.lorrainelanting.maj.ui.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.OrderGroup
import com.lorrainelanting.maj.data.util.*
import com.lorrainelanting.maj.databinding.ItemOrdersContentBinding

class OrdersContentAdapter(
    private var dataset: List<OrderGroup>,
    private val ordersContentAdapterListener: OrdersContentAdapterListener? = null
) : RecyclerView.Adapter<OrdersContentAdapter.OrdersContentViewHolder>() {

    private var orderType = COMPLETED_ORDERS

    companion object {
        const val ACTIVE_ORDERS = 0
        const val COMPLETED_ORDERS = 1
        var count = 0
    }

    class OrdersContentViewHolder(
        private val binding: ItemOrdersContentBinding,
        private val ordersContentAdapterListener: OrdersContentAdapterListener? = null
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(orderGroup: OrderGroup) {
            binding.txtViewMoreProduct.text = itemView.context.getString(
                R.string.txt_order_count,
                (orderGroup.size ?: 0).toString()
            )

            binding.btnViewOrders.visibility = View.VISIBLE
            binding.btnMoveToCompleted.visibility = View.GONE
//            if (orderGroup.status == Constants.STATUS_PLACED_ORDER) {
//                binding.btnViewOrders.visibility = View.GONE
//                binding.btnMoveToCompleted.visibility = View.VISIBLE
//            } else {
//                binding.btnViewOrders.visibility = View.VISIBLE
//                binding.btnMoveToCompleted.visibility = View.GONE
//            }
            binding.cardOrdersContentPast.setOnClickListener {
                ordersContentAdapterListener?.onOrderCardClick(orderGroup)
            }

            binding.btnViewOrders.setOnClickListener {
                ordersContentAdapterListener?.onViewOrdersBtnClick(orderGroup)
            }

            binding.btnMoveToCompleted.setOnClickListener {
                ordersContentAdapterListener?.onMoveToCompletedBtnClick(orderGroup)
            }
            bindOrderStatus(orderGroup)
        }

        private fun bindOrderStatus(orderGroup: OrderGroup) {
            if (orderGroup.status == STATUS_DELIVERED || orderGroup.status == STATUS_PICKED_UP) {
                binding.txtOrderStatus.visibility = View.VISIBLE
                when (orderGroup.deliveryOption) {
                    OPTION_DELIVER -> {
                        binding.txtOrderStatus.text = STATUS_DELIVERED
                    }
                    OPTION_PICK_UP -> {
                        binding.txtOrderStatus.text = STATUS_PICKED_UP
                    }
                }
            }
            if (orderGroup.status == STATUS_PLACED_ORDER) {
                binding.txtOrderStatus.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrdersContentViewHolder {
        val content =
            ItemOrdersContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrdersContentViewHolder(
            content,
            ordersContentAdapterListener
        )
    }

    override fun getItemCount(): Int {
        return getFilteredDataSet().size
    }

    override fun onBindViewHolder(holder: OrdersContentViewHolder, position: Int) {
        val item = getFilteredDataSet()[position]
        holder.bind(item)
    }

    fun update(dataSet: List<OrderGroup>) {
        this.dataset = dataSet
        notifyDataSetChanged()
    }

    private fun getFilteredDataSet(): List<OrderGroup> {
        return when (orderType) {
            ACTIVE_ORDERS -> {
                dataset.filter {
                    it.status == STATUS_PLACED_ORDER
                }
            }
            COMPLETED_ORDERS -> {
                dataset.filter {
                    it.status == STATUS_DELIVERED || it.status == STATUS_PICKED_UP
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
        fun onOrderCardClick(orderGroup: OrderGroup)

        fun onMoveToCompletedBtnClick(orderGroup: OrderGroup)

        fun onViewOrdersBtnClick(orderGroup: OrderGroup)
    }
}