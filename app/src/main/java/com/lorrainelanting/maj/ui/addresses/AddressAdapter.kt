package com.lorrainelanting.maj.ui.addresses

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.lorrainelanting.maj.databinding.ItemAddressBinding
import java.util.*
import kotlin.collections.ArrayList

class AddressAdapter(
    private var list: ArrayList<String>,
    private val addressAdapterListener: AddressAdapterListener? = null
) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>(), Filterable {

    var addressList = ArrayList<String>()

    init {
        addressList = list
    }

    class AddressViewHolder(
        private val binding: ItemAddressBinding,
        private val addressAdapterListener: AddressAdapterListener? = null
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.txtAddressItem.text = item
            binding.txtAddressItem.setOnClickListener {
                addressAdapterListener?.onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val address =
            ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressViewHolder(address, addressAdapterListener)
    }

    override fun getItemCount(): Int {
        return addressList.size
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val address = addressList[position]
        holder.bind(address)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    addressList = list
                } else {
                    val resultList = ArrayList<String>()
                    for (row in list) {
                        if (row.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    addressList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = addressList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                addressList = results?.values as ArrayList<String>
                notifyDataSetChanged()
            }
        }
    }

    fun update(list: ArrayList<String>) {
        this.list = list
        notifyDataSetChanged()
    }

    interface AddressAdapterListener {
        fun onItemClick(item: String)
    }
}