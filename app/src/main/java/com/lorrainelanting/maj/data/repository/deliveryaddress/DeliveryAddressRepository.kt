package com.lorrainelanting.maj.data.repository.deliveryaddress

import androidx.lifecycle.LiveData
import com.lorrainelanting.maj.data.model.DeliveryAddress

interface DeliveryAddressRepository {
    fun getList() : LiveData<List<DeliveryAddress>>

    fun getDeliveryAddress(id: String) : LiveData<DeliveryAddress>

    suspend fun saveCity(city: String)

    suspend fun saveBarangay(barangay: String)

    suspend fun saveStreet(street: String)

    suspend fun saveOtherNotes(notes: String)

    fun update(deliveryAddress: DeliveryAddress)

    fun delete(id: String)
}