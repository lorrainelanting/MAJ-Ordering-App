package com.vitocuaderno.maj.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.observe
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
        setEventsAddToCartLayout()
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
        binding.layoutAddToCart.visibility = View.VISIBLE
    }

    private fun setEventsAddToCartLayout() {
        //        TODO Decrease quantity
        binding.layoutAddToCart.findViewById<FrameLayout>(R.id.btnMinus).setOnClickListener {
            Toast.makeText(this.context, "Minus Button clicked! TODO: Decrease quantity", Toast.LENGTH_SHORT).show()
        }
        //        TODO Increase quantity
        binding.layoutAddToCart.findViewById<FrameLayout>(R.id.btnAdd).setOnClickListener {
            Toast.makeText(this.context, "Add Button clicked! TODO: Increase quantity", Toast.LENGTH_SHORT).show()
        }
        //        TODO Item added to cart
        binding.layoutAddToCart.findViewById<Button>(R.id.btnAddToCart).setOnClickListener {
            Toast.makeText(this.context, "TODO: Item added to cart", Toast.LENGTH_SHORT).show()
        }
    }
}