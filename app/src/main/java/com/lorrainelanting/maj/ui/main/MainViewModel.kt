package com.lorrainelanting.maj.ui.main

import com.lorrainelanting.maj.ui.base.BaseViewModel

class MainViewModel : BaseViewModel() {
    val cartContentsLiveData by lazy { cartRepository.getList() }
    val ordersContentLiveData by lazy { ordersRepository.getOrderGroupList() }
}