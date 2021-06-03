package com.lorrainelanting.maj.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.DeliveryAddress
import com.lorrainelanting.maj.data.model.User
import com.lorrainelanting.maj.databinding.FragmentProfileBinding
import com.lorrainelanting.maj.ui.base.BaseFragment
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
                        binding.editTextContactNum.setText(user.contactNum)
                    }
                }
            }
        }

        viewModel.deliveryAddressLiveData.observe(viewLifecycleOwner) { list ->
            list.let {
                if (list.isNotEmpty()) {
                    binding.txtLayoutSetAddress.visibility = View.GONE
                    binding.txtLayoutAddress.visibility = View.VISIBLE
                    var deliveryAddress: String
                    for (delAddress in list) {
                        deliveryAddress = "${delAddress.streetName}, ${delAddress.barangay}, ${delAddress.city}"
                        binding.editTxtDelAddress.setText(deliveryAddress)
                    }
                } else {
                    binding.txtLayoutSetAddress.visibility = View.VISIBLE
                    binding.txtLayoutAddress.visibility = View.GONE
                }
            }
        }

        binding.editTxtSetAddress.setOnClickListener {
            startCityActivity()
        }

        binding.txtLayoutAddress.setEndIconOnClickListener {
            startCityActivity()
        }

        binding.btnProfileSave.setOnClickListener {
            val storeName = binding.editTextStoreName.text.toString()
            val customerName = binding.editTextFullName.text.toString()
            val contactNum = binding.editTextContactNum.text.toString()
            val otherNotes = binding.editTextNotes.text.toString()

            if (storeName.isNotEmpty() || customerName.isNotEmpty() || contactNum.isNotEmpty()) {
                val user = User()
                user.storeName = storeName
                user.fullName = customerName
                user.contactNum = contactNum
                viewModel.userRepository.save(user)
            }

            if (otherNotes.isNotEmpty()) {
                val delAdd = DeliveryAddress()
                delAdd.otherNotes = otherNotes
                viewModel.deliveryAddressRepository.saveOtherNotes(delAdd.otherNotes)
            }
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