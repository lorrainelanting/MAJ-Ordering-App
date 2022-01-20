package com.lorrainelanting.maj.ui.addresses

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.databinding.ActivitySetStreetBinding
import com.lorrainelanting.maj.ui.base.BaseActivity

class SetStreetActivity : BaseActivity<ActivitySetStreetBinding>() {
    private var viewModel = DeliveryAddressViewModel()

    override fun getLayoutId(): Int = R.layout.activity_set_street

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.injectDeliveryAddress(this)

        binding.txtAddressHouse.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim { it <= ' ' }.isNotEmpty()) {
                    binding.btnProfileSave.isEnabled = true
                    binding.btnProfileSave.setOnClickListener {
                        viewModel.repository.saveStreet(s.toString())
                        setResult(1)
                        finish()
                    }
                } else {
                    binding.btnProfileSave.isEnabled = false
                }
            }
        })
    }
}