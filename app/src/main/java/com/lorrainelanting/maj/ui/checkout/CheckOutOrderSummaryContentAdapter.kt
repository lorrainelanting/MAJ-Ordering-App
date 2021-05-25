package com.lorrainelanting.maj.ui.checkout

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.CartContent
import com.lorrainelanting.maj.data.model.Product
import com.lorrainelanting.maj.data.util.CurrencyUtil
import com.lorrainelanting.maj.databinding.ItemCheckoutOrderSummaryBinding
import com.squareup.picasso.Picasso

class CheckOutOrderSummaryContentAdapter(
    private var dataSet: List<Content>,
    private val adapterCalculation: CheckOutOrderSummaryContentAdapterCalculation? = null
) : RecyclerView.Adapter<CheckOutOrderSummaryContentAdapter.OrderSummaryContentViewHolder>() {
    class OrderSummaryContentViewHolder(
        private val binding: ItemCheckoutOrderSummaryBinding,
        private val adapterCalculation: CheckOutOrderSummaryContentAdapterCalculation? = null
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(content: Content) {
            var picasso = Picasso.get()
            picasso.load(content.product.imgUrl).placeholder(R.color.colorSecondary)
                .error(R.drawable.ic_soft_drink).into(binding.imgProduct)
            binding.txtProductDescription.text = "${content.product.name}  ${content.product.packQty}"
            binding.txtValUnitCost.text = CurrencyUtil.format(content.product.price)
            binding.txtResultQuantity.text = content.cartContent.quantity.toString()
            adapterCalculation?.getTotal(content.product.price, content.cartContent.quantity)
                ?.let { total ->
                    binding.txtResultTotal.text = CurrencyUtil.format(total)
                }
        }
    }

    class Content(
        val cartContent: CartContent,
        val product: Product
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderSummaryContentViewHolder {
        val orderSummaryContent = ItemCheckoutOrderSummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderSummaryContentViewHolder(
            orderSummaryContent,
            adapterCalculation
        )
    }

    override fun onBindViewHolder(holder: OrderSummaryContentViewHolder, position: Int) {
        val orderSummaryContent = dataSet[position]
        holder.bind(orderSummaryContent)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun update(dataSet: List<Content>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    interface CheckOutOrderSummaryContentAdapterCalculation {
        fun getTotal(unitPrice: Double, quantity: Int): Double
    }
}