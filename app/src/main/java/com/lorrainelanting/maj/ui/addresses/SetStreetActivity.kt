package com.lorrainelanting.maj.ui.addresses

import android.os.Bundle
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.databinding.ActivitySetStreetBinding
import com.lorrainelanting.maj.ui.BaseActivity

class SetStreetActivity : BaseActivity<ActivitySetStreetBinding>() {
    private var viewModel = DeliveryAddressViewModel()

    override fun getLayoutId(): Int = R.layout.activity_set_street

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.injectDeliveryAddress(this)

        binding.btnProfileSave.setOnClickListener {
            val streetName = binding.txtAddressHouse.text.toString()
            viewModel.repository.saveStreet(streetName)
            setResult(1)
            finish()
        }

        binding.layoutToolbar.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}