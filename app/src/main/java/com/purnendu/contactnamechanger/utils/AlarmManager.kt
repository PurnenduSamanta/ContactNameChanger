package com.purnendu.contactnamechanger.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class AlarmManager(private val context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun schedule(requestCode: Int, triggerTimeInMillis: Long): Boolean {

        if (triggerTimeInMillis < System.currentTimeMillis()) {
            return false
        }
        val pendingIntent = getPendingIntent(requestCode)
        /*val alarmClockInfo = AlarmManager.AlarmClockInfo(triggerTimeInMillis, pendingIntent)
        alarmManager.setAlarmClock(alarmClockInfo, pendingIntent)
        return true*/


        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP, // Use RTC_WAKEUP to wake the device
            triggerTimeInMillis,
            pendingIntent
        )
        return true
    }

    private fun getPendingIntent(requestCode: Int): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            requestCode,
            getIntent(),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun getIntent(): Intent {
        return Intent(context, AlarmReceiver::class.java)
    }

}