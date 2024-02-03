package com.lorrainelanting.maj.ui.profile

import androidx.lifecycle.viewModelScope
import com.lorrainelanting.maj.data.model.User
import com.lorrainelanting.maj.data.repository.deliveryaddress.DeliveryAddressRepository
import com.lorrainelanting.maj.data.repository.user.UserRepository
import com.lorrainelanting.maj.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val deliveryAddressRepository: DeliveryAddressRepository
) : BaseViewModel() {
    val usersLiveData by lazy { userRepository.getList() }
    val deliveryAddressLiveData by lazy { deliveryAddressRepository.getList() }

    fun insertUser(user: User) {
        userRepository.save(user)
    }

    fun insertDeliveryNote(notes: String) {
        viewModelScope.launch {
            deliveryAddressRepository.saveOtherNotes(notes)
        }
    }
}