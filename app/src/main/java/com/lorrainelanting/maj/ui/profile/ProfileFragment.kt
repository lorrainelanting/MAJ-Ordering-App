package com.lorrainelanting.maj.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.databinding.FragmentProfileBinding
import com.lorrainelanting.maj.ui.BaseFragment
import com.lorrainelanting.maj.ui.addresses.SetCityActivity

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private var viewModel = ProfileViewModel()

    override fun getLayoutId(): Int = R.layout.fragment_profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.deliveryAddressLiveData.observe(viewLifecycleOwner) {list ->
            list.let {
                if (list.isNotEmpty()) {
                    binding.txtSetAddress.visibility = View.GONE
                    binding.txtLayoutAddress.visibility = View.VISIBLE
                    var deliveryAddress: String
                    for (delAddress in list) {
                        deliveryAddress = "${delAddress.streetName}, ${delAddress.barangay}, ${delAddress.city}"
                        binding.editTxtDelAddress.setText(deliveryAddress)
                    }
                } else {
                    binding.txtSetAddress.visibility = View.VISIBLE
                    binding.txtLayoutAddress.visibility = View.GONE
                }
            }
        }

        binding.txtSetAddress.setOnClickListener {
            startActivity()
        }

        binding.txtLayoutAddress.setEndIconOnClickListener {
            startActivity()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.injectDeliveryAddress(context)
    }

    private fun startActivity() {
        val intent = Intent(context, SetCityActivity().javaClass)
        context?.startActivity(intent)
    }
}