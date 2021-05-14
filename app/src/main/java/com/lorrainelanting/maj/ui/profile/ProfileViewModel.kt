package com.lorrainelanting.maj.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import com.lorrainelanting.maj.data.repository.deliveryaddress.DeliveryAddressRepository
import com.lorrainelanting.maj.di.Injection

class ProfileViewModel : ViewModel() {
    lateinit var deliveryAddressRepository: DeliveryAddressRepository

    val deliveryAddressLiveData by lazy {
        deliveryAddressRepository.getList()
    }

    fun injectDeliveryAddress(context: Context) {
        deliveryAddressRepository = Injection.provideDeliveryAddressRepository(context)
    }
}