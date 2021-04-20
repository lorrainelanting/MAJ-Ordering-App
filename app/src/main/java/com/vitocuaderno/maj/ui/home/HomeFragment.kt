package com.vitocuaderno.maj.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.vitocuaderno.maj.R
import com.vitocuaderno.maj.data.model.HomeContent
import com.vitocuaderno.maj.data.repository.Callback
import com.vitocuaderno.maj.data.repository.HomeContentRepository
import com.vitocuaderno.maj.databinding.FragmentHomeBinding
import com.vitocuaderno.maj.di.Injection
import com.vitocuaderno.maj.ui.BaseFragment
import com.vitocuaderno.maj.ui.ProductDetailActivity
import java.lang.Exception

class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeContentsAdapter.HomeAdapterListener {
    lateinit var repository: HomeContentRepository
    override fun onAttach(context: Context) {
        super.onAttach(context)
        repository = Injection.provideHomeContentRepository(context)
    }
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
        repository.getList(object: Callback<List<HomeContent>> {
            override fun onComplete(result: List<HomeContent>) {
                homeContents.addAll(result)
                //        TODO: Hide loading
                adapter?.notifyDataSetChanged()
            }

            override fun onError(exception: Exception) {
               // TODO("Not yet implemented")
            }

        })
    }

    override fun onItemClick(homeContent: HomeContent) {
        val intent = Intent(this.context, ProductDetailActivity().javaClass)
        intent.putExtra("id", homeContent.id)
        context?.startActivity(intent)
        Toast.makeText(this.context, "TODO: Item added to cart.", Toast.LENGTH_SHORT).show()
    }
}