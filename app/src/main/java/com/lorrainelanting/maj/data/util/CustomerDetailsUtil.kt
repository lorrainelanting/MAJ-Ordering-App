package com.lorrainelanting.maj.data.util

object CustomerDetailsUtil {
    fun isCustomerDetailsSet(name: String, contactNum: String): Boolean {
        return !(name.isEmpty() || contactNum.isEmpty())
    }

    fun isDeliveryAddressSet(address: String): Boolean {
        return address.isNotEmpty()
    }
}