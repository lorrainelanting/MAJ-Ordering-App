package com.vitocuaderno.maj.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.vitocuaderno.maj.R
import com.vitocuaderno.maj.data.model.HomeContent
import com.vitocuaderno.maj.databinding.FragmentHomeBinding
import com.vitocuaderno.maj.ui.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeContentsAdapter.HomeAdapterListener {

    override fun getLayoutId(): Int = R.layout.fragment_home

    var adapter: HomeContentsAdapter? = null
    var homeContents = mutableListOf<HomeContent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = HomeContentsAdapter(homeContents, this)
        binding.rvHomeContents.adapter = adapter
        fetchHomeContents()
    }

    private fun fetchHomeContents() {
        for (i in 0..10) {
            val homeContent = HomeContent(i)
//            homeContent.productImgUrl = ""
            homeContent.productDescription = "Coke 1.5L"
            homeContent.productUnitCost = 168.00
            homeContent.productPackQty = "${12}pcs. per pack"
            homeContents.add(homeContent)
        }
        //        TODO: Hide loading
        adapter?.notifyDataSetChanged()
    }

    override fun onItemClick(homeContent: HomeContent) {
        val intent = Intent(this.context, HomeActivity().javaClass)
        context?.startActivity(intent)
        Toast.makeText(this.context, "TODO: Item added to cart.", Toast.LENGTH_SHORT).show()
    }
}