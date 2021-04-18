package com.ramazan.mediasearch.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {


    fun formatServerUTCDateTimeLocal(date: String): String {
        val date1 =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(date)
        val calendar = Calendar.getInstance().apply { time = date1!! }
        val year = calendar.get(Calendar.YEAR)
        val month = String.format("%02d", calendar.get(Calendar.MONTH) + 1)
        val day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))
        return "$day.$month.$year"
    }

}