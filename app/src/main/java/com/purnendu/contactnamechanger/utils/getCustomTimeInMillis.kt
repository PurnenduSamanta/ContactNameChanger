package com.purnendu.contactnamechanger.utils

import java.util.Calendar

fun getCustomTimeInMillis(hour: Int, minute: Int, second: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, minute)
    calendar.set(Calendar.SECOND, second)
    calendar.set(Calendar.MILLISECOND, 0) // Optional: If you want to clear milliseconds

    return calendar.timeInMillis
}