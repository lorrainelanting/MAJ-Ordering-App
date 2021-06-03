package com.lorrainelanting.maj.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import com.lorrainelanting.maj.data.repository.deliveryaddress.DeliveryAddressRepository
import com.lorrainelanting.maj.data.repository.user.UserRepository
import com.lorrainelanting.maj.di.Injection

class ProfileViewModel : ViewModel() {
    lateinit var deliveryAddressRepository: DeliveryAddressRepository

    lateinit var userRepository: UserRepository

    val deliveryAddressLiveData by lazy {
        deliveryAddressRepository.getList()
    }

    val userLiveData by lazy {
        userRepository.getList()
    }

    fun injectDeliveryAddress(context: Context) {
        deliveryAddressRepository = Injection.provideDeliveryAddressRepository(context)
    }

    fun injectUser(context: Context) {
        userRepository = Injection.provideUserRepository(context)
    }

}