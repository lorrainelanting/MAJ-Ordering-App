package com.lorrainelanting.maj.ui.addresses

import android.content.Context
import androidx.lifecycle.ViewModel
import com.lorrainelanting.maj.data.repository.deliveryaddress.DeliveryAddressRepository
import com.lorrainelanting.maj.di.Injection

class DeliveryAddressViewModel : ViewModel() {
    lateinit var repository: DeliveryAddressRepository

    fun injectDeliveryAddress(context: Context) {
        repository = Injection.provideDeliveryAddressRepository(context)
    }
}