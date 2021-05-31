package com.lorrainelanting.maj.data.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {
        fun formatToString(date: Date) : String {
            val formatter = SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH)
            return formatter.format(date)
        }

        fun formatToDate(dateString: Date) : Long {
            val formatter = SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH).parse(formatToString(dateString))
            return formatter.time
        }
    }
}