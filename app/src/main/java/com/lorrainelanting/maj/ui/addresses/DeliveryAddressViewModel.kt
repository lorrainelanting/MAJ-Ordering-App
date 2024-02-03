package com.lorrainelanting.maj.ui.addresses

import androidx.lifecycle.viewModelScope
import com.lorrainelanting.maj.data.repository.deliveryaddress.DeliveryAddressRepository
import com.lorrainelanting.maj.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeliveryAddressViewModel @Inject constructor(
    private val deliveryAddressRepository: DeliveryAddressRepository
    ) : BaseViewModel() {

    fun saveBarangay(barangay: String) {
        viewModelScope.launch {
            deliveryAddressRepository.saveBarangay(barangay)
        }
    }

    fun saveCity(city: String) {
        viewModelScope.launch {
            deliveryAddressRepository.saveCity(city)
        }
    }

    fun saveStreet(street: String) {
        viewModelScope.launch {
            deliveryAddressRepository.saveStreet(street)
        }
    }
}