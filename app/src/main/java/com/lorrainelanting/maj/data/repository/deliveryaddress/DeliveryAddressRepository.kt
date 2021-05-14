package com.lorrainelanting.maj.data.repository.deliveryaddress

import androidx.lifecycle.LiveData
import com.lorrainelanting.maj.data.model.DeliveryAddress

interface DeliveryAddressRepository {
    fun getList() : LiveData<List<DeliveryAddress>>

    fun getDeliveryAddress(id: Int) : LiveData<DeliveryAddress>

    fun saveCity(city: String)

    fun saveBarangay(barangay: String)

    fun saveStreet(street: String)

    fun update(deliveryAddress: DeliveryAddress)

    fun delete(id: Int)
}