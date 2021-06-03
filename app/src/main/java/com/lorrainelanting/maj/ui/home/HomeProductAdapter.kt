package com.lorrainelanting.maj.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.Product
import com.lorrainelanting.maj.data.util.CurrencyUtil
import com.lorrainelanting.maj.databinding.ItemHomeContentBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class HomeProductAdapter(
    private var dataSet: List<Product>,
    private val homeAdapterListener: HomeAdapterListener? = null
) :
    RecyclerView.Adapter<HomeProductAdapter.HomeContentViewHolder>() {

    private var sortPrice = -1

    class HomeContentViewHolder(
        private val binding: ItemHomeContentBinding,
        private val homeAdapterListener: HomeAdapterListener? = null
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(content: Product) {
        //  Load image from url string
            val picasso = Picasso.get()
            picasso.load(content.imgUrl).error(R.drawable.ic_homepage).into(
                binding.imgProduct,
                object : Callback {
                    override fun onSuccess() {}

                    override fun onError(e: Exception?) {
                        e?.printStackTrace()
                    }
                }
            )

            binding.txtProductDescription.text = content.name
            binding.txtUnitCost.text = CurrencyUtil.format(content.price)
            binding.txtProductPackQty.text = ""

            binding.imgProduct.setOnClickListener { item ->
                homeAdapterListener?.onItemClick(content)
            }

            binding.btnAddToCart.setOnClickListener { item ->
                homeAdapterListener?.onAddToCartBtnClick(content)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeContentViewHolder {
        val content =
            ItemHomeContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeContentViewHolder(content, homeAdapterListener)
    }

    override fun onBindViewHolder(
        holder: HomeContentViewHolder,
        position: Int
    ) {
        val item = getFilteredDataSet()[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return getFilteredDataSet().size
    }

    interface HomeAdapterListener {
        /**
         * Home product click listener
         * @param product
         * */
        fun onItemClick(product: Product)

        /**
         * Add to cart button click listener
         * @param product
         * */
        fun onAddToCartBtnClick(product: Product)
    }

    fun update(dataSet: List<Product>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    fun setSortedPrice(sortPriceBy: Int) {
        sortPrice = sortPriceBy
        notifyDataSetChanged()
    }

    private fun getFilteredDataSet(): List<Product> {
        return when (sortPrice) {
            SORT_LOW_TO_HIGH -> {
                dataSet.sortedBy {
                    it.price
                }
            }
            SORT_HIGH_TO_LOW -> {
                dataSet.sortedByDescending {
                    it.price
                }
            }
            else -> {
                dataSet
            }
        }
    }

    companion object {
        const val SORT_LOW_TO_HIGH = 0
        const val SORT_HIGH_TO_LOW = 1
    }
}