package com.vitocuaderno.maj.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.vitocuaderno.maj.R
import com.vitocuaderno.maj.data.model.Product
import com.vitocuaderno.maj.data.util.CurrencyUtil
import com.vitocuaderno.maj.databinding.ItemHomeContentBinding

class HomeProductAdapter(
    private val dataSet: MutableList<Product>,
    private val homeAdapterListener: HomeAdapterListener? = null
) :
    RecyclerView.Adapter<HomeProductAdapter.HomeContentViewHolder>() {

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

            binding.txtProductDescription.text = content.description
            binding.txtUnitCost.text = CurrencyUtil.format(content.unitCost)
            binding.txtProductPackQty.text = content.packQty

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
        val item = dataSet[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataSet.size
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
}