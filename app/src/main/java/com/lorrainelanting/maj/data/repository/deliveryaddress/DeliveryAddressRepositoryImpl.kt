package com.lorrainelanting.maj.data.repository.deliveryaddress

import androidx.lifecycle.LiveData
import com.lorrainelanting.maj.data.local.DeliveryAddressDao
import com.lorrainelanting.maj.data.model.DeliveryAddress

class DeliveryAddressRepositoryImpl private constructor(private val dao: DeliveryAddressDao) :
    DeliveryAddressRepository {

    companion object {
        private var instance: DeliveryAddressRepository? = null

        fun getInstance(dao: DeliveryAddressDao): DeliveryAddressRepository {
            if (instance == null) {
                instance =
                    DeliveryAddressRepositoryImpl(dao)
            }
            return instance!!
        }
    }

    override fun getList(): LiveData<List<DeliveryAddress>> = dao.getList()

    override fun getDeliveryAddress(id: String): LiveData<DeliveryAddress> = dao.getAddress(id)

    override suspend fun saveCity(city: String) {
        val existingAddress = dao.getDeliveryAddress()
        if (existingAddress == null) {
            val deliveryAddress = DeliveryAddress()
            deliveryAddress.id = "0"
            deliveryAddress.city = city
            dao.insert(deliveryAddress)
        } else {
            existingAddress.city = city
            dao.update(existingAddress)
        }
    }

    override suspend fun saveBarangay(barangay: String) {
        val existingAddress = dao.getDeliveryAddress()
        if (existingAddress == null) {
            val deliveryAddress = DeliveryAddress()
            deliveryAddress.id = "0"
            deliveryAddress.barangay = barangay
            dao.insert(deliveryAddress)
        } else {
            existingAddress.barangay = barangay
            dao.update(existingAddress)
        }
    }

    override suspend fun saveStreet(street: String) {
        val existingAddress = dao.getDeliveryAddress()
        if (existingAddress == null) {
            val deliveryAddress = DeliveryAddress()
            deliveryAddress.id = "0"
            deliveryAddress.streetName = street
            dao.insert(deliveryAddress)
        } else {
            existingAddress.streetName = street
            dao.update(existingAddress)
        }
    }

    override suspend fun saveOtherNotes(notes: String) {
        val existingAddress = dao.getDeliveryAddress()
        if (existingAddress == null) {
            val deliveryAddress = DeliveryAddress()
            deliveryAddress.id = "0"
            deliveryAddress.otherNotes = notes
            dao.insert(deliveryAddress)
        } else {
            existingAddress.otherNotes = notes
            dao.update(existingAddress)
        }
    }

    override fun update(deliveryAddress: DeliveryAddress) = dao.update(deliveryAddress)

    override fun delete(id: String) = dao.delete(id)
}