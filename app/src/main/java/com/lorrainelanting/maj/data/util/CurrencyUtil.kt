package com.lorrainelanting.maj.data.util

import java.text.DecimalFormat

class CurrencyUtil {
    companion object {
        fun format(amount: Double) : String {
            val formatter = DecimalFormat("###,###,##0.00")
            return "${CURRENCY} ${formatter.format(amount)}"
        }
    }
}