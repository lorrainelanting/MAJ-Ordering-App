package com.lorrainelanting.maj.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.User
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

        viewModel.userLiveData.observe(viewLifecycleOwner) { list ->
            list.let {
                if (list.isNotEmpty()) {
                    for (user in list) {
                        binding.editTextStoreName.setText(user.storeName)
                        binding.editTextFullName.setText(user.fullName)
                        binding.editTextContactNum.setText(user.contactNum.toString())
                    }
                }
            }
        }

        viewModel.deliveryAddressLiveData.observe(viewLifecycleOwner) { list ->
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
            startCityActivity()
        }

        binding.txtLayoutAddress.setEndIconOnClickListener {
            startCityActivity()
        }

        binding.btnProfileSave.setOnClickListener {
            var user = User()
            user.storeName = binding.editTextStoreName.text.toString()
            user.fullName = binding.editTextFullName.text.toString()
            user.contactNum = binding.editTextContactNum.text.toString().toLong()
            viewModel.userRepository.save(user)

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.injectDeliveryAddress(context)
        viewModel.injectUser(context)
    }

    private fun startCityActivity() {
        val intent = Intent(context, SetCityActivity().javaClass)
        context?.startActivity(intent)
    }
}