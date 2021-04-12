package com.vitocuaderno.maj.data.util

import java.text.DecimalFormat

class CurrencyUtil {
    companion object {
        fun format(amount: Double) : String {
            val formatter = DecimalFormat("###,###,##0.00")
            return "${Constants.currency} ${formatter.format(amount)}"
        }
    }
}