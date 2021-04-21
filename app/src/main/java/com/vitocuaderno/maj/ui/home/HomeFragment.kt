package com.vitocuaderno.maj.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.vitocuaderno.maj.R
import com.vitocuaderno.maj.data.model.HomeContent
import com.vitocuaderno.maj.data.repository.HomeContentRepository
import com.vitocuaderno.maj.databinding.FragmentHomeBinding
import com.vitocuaderno.maj.di.Injection
import com.vitocuaderno.maj.ui.BaseFragment
import com.vitocuaderno.maj.ui.ProductDetailActivity

class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeContentsAdapter.HomeAdapterListener {
    lateinit var repository: HomeContentRepository
    var adapter: HomeContentsAdapter? = null
    var homeContents = mutableListOf<HomeContent>()

    private val homeContentsLiveData by lazy {
        repository.getList()
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = HomeContentsAdapter(homeContents, this)
        binding.rvHomeContents.adapter = adapter
        homeContentsLiveData.observe(viewLifecycleOwner) {it ->
            it.let {
                homeContents.addAll(it)
                //        TODO: Hide loading
                adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        repository = Injection.provideHomeContentRepository(context)
    }

    override fun onItemClick(homeContent: HomeContent) {
        val intent = Intent(this.context, ProductDetailActivity().javaClass)
        intent.putExtra(ProductDetailActivity.ID, homeContent.id)
        repository.getItem(homeContent.id)
        context?.startActivity(intent)
    }

    override fun onAddToCartBtnClick(homeContent: HomeContent) {
        Toast.makeText(this.context, "TODO: Item added to cart.", Toast.LENGTH_SHORT).show()
    }
}