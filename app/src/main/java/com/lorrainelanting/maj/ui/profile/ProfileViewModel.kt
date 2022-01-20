package com.lorrainelanting.maj.ui.profile

import com.lorrainelanting.maj.data.model.User
import com.lorrainelanting.maj.ui.base.BaseViewModel

class ProfileViewModel : BaseViewModel() {
    val usersLiveData by lazy { userRepository.getList() }
    val deliveryAddressLiveData by lazy { deliveryAddressRepository.getList() }

    fun insertUser(user: User) {
        userRepository.save(user)
    }

    fun insertDeliveryNote(notes: String) {
        deliveryAddressRepository.saveOtherNotes(notes)
    }
}