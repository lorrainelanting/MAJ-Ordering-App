package com.lorrainelanting.maj.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.lorrainelanting.maj.R
import com.lorrainelanting.maj.data.model.DeliveryAddress
import com.lorrainelanting.maj.data.model.User
import com.lorrainelanting.maj.databinding.FragmentProfileBinding
import com.lorrainelanting.maj.ui.addresses.SetCityActivity
import com.lorrainelanting.maj.ui.base.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private var viewModel = ProfileViewModel()

    override fun getLayoutId(): Int = R.layout.fragment_profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.usersLiveData.observe(viewLifecycleOwner) { users ->
            val editTextStoreName = binding.editTextStoreName
            val editTextFullName = binding.editTextFullName
            val editTextContactNum = binding.editTextContactNum
            users.let {
                if (users.isNotEmpty()) {
                    for (user in users) {
                        editTextStoreName.setText(user.storeName)
                        editTextFullName.setText(user.fullName)
                        editTextContactNum.setText(user.contactNum)
                    }
                }
            }
            setEditTextSelection(editTextStoreName, editTextFullName, editTextContactNum)
        }

        viewModel.deliveryAddressLiveData.observe(viewLifecycleOwner) { deliveryAddresses ->
            deliveryAddresses.let {
                if (deliveryAddresses.isNotEmpty()) {
                    binding.txtLayoutSetAddress.visibility = View.GONE
                    binding.txtLayoutAddress.visibility = View.VISIBLE
                    var deliveryAddress: String
                    for (delAddress in deliveryAddresses) {
                        deliveryAddress =
                            "${delAddress.streetName}, ${delAddress.barangay}, ${delAddress.city}"
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
                viewModel.insertUser(user)
            }

            if (otherNotes.isNotEmpty()) {
                val delAdd = DeliveryAddress()
                delAdd.otherNotes = otherNotes
                viewModel.insertDeliveryNote(delAdd.otherNotes)
            }

            if (storeName.isNotEmpty() || customerName.isNotEmpty() || contactNum.isNotEmpty() || otherNotes.isNotEmpty()) {
                Toast.makeText(requireContext(), "Profile save successfully.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.initializedRepositories(context)
    }

    private fun setEditTextSelection(
        editTextStoreName: TextInputEditText,
        editTextFullName: TextInputEditText,
        editTextContactNum: TextInputEditText
    ) {
        editTextStoreName.setSelection(editTextStoreName.text?.length!!)
        editTextFullName.setSelection(editTextFullName.text?.length!!)
        editTextContactNum.setSelection(editTextContactNum.text?.length!!)
    }

    private fun startCityActivity() {
        val intent = Intent(context, SetCityActivity().javaClass)
        context?.startActivity(intent)
    }
}