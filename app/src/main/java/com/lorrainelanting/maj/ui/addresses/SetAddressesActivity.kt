package com.lorrainelanting.maj.ui.addresses

import android.os.Bundle
import android.widget.SearchView
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.DeliveryAddress
import com.lorrainelanting.maj.databinding.ActivitySetAddressesBinding
import com.lorrainelanting.maj.ui.base.BaseActivity

abstract class SetAddressesActivity : BaseActivity<ActivitySetAddressesBinding>(), AddressAdapter.AddressAdapterListener {
    protected var list = ArrayList<String>()
    protected var viewModel = DeliveryAddressViewModel()

    var adapter: AddressAdapter? = null

    override fun getLayoutId(): Int = R.layout.activity_set_addresses

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        populate()
        adapter = AddressAdapter(list,this)
        binding.rvAddresses.adapter = adapter

        adapter?.update(list)

        viewModel.injectDeliveryAddress(this)

//        val delAddress = DeliveryAddress()
//        viewModel.getDeliveryAddressLiveData(delAddress.id)

        binding.layoutToolbarSearch.searchViewAddress.setOnQueryTextListener(onSearchQuery())
    }

    abstract fun populate()

    private fun onSearchQuery(): androidx.appcompat.widget.SearchView.OnQueryTextListener? {
        return object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (list.contains(query)) {
                    adapter?.filter?.filter(query)
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter?.filter?.filter(newText)
                return false
            }
        }
    }
}