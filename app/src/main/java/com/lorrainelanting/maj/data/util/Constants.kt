package com.lorrainelanting.maj.data.util

class Constants {
    companion object {
        const val CURRENCY: String = "₱"

        // Delivery Options
        const val DELIVER: Int = 1
        const val PICK_UP: Int = 2

        const val DELIVERY_DATE: String = "Delivery Date"
        const val PICK_UP_DATE: String = "Pick-up Date"

        // Order Status
        const val DELIVERED: String = "Delivered"
        const val PICKED_UP: String = "Picked-Up"
        const val PLACED_ORDER = "Placed Order"

        // Date Format
        const val DATE_FORMAT = "dd MMM yyyy" // 01 Jan 2021
    }
}