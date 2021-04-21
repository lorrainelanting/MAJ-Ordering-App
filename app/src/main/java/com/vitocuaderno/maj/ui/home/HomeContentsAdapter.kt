package com.vitocuaderno.maj.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.vitocuaderno.maj.R
import com.vitocuaderno.maj.data.model.HomeContent
import com.vitocuaderno.maj.data.util.CurrencyUtil
import com.vitocuaderno.maj.databinding.ItemHomeContentBinding

class HomeContentsAdapter(
    private val dataSet: MutableList<HomeContent>,
    private val homeAdapterListener: HomeAdapterListener? = null
) :
    RecyclerView.Adapter<HomeContentsAdapter.HomeContentViewHolder>() {

    class HomeContentViewHolder(
        private val binding: ItemHomeContentBinding,
        private val homeAdapterListener: HomeAdapterListener? = null
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(content: HomeContent) {
        //  Load image from url string
            val picasso = Picasso.get()
            picasso.load(content.productImgUrl).error(R.drawable.ic_homepage).into(
                binding.imgProduct,
                object : Callback {
                    override fun onSuccess() {}

                    override fun onError(e: Exception?) {
                        e?.printStackTrace()
                    }
                }
            )

            binding.txtProductDescription.text = content.productDescription
            binding.txtUnitCost.text = CurrencyUtil.format(content.productUnitCost)
            binding.txtProductPackQty.text = content.productPackQty

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
        fun onItemClick(homeContent: HomeContent)

        fun onAddToCartBtnClick(homeContent: HomeContent)
    }
}