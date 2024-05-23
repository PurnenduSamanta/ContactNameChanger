package com.purnendu.contactnamechanger.utils.alarmManager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class AlarmManager(private val context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun schedule(requestCode: Int, triggerTimeInMillis: Long,alarmId: String): Boolean {

        if (triggerTimeInMillis < System.currentTimeMillis()) {
            return false
        }
        val pendingIntent = getPendingIntent(requestCode,alarmId)

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP, // Use RTC_WAKEUP to wake the device
            triggerTimeInMillis,
            pendingIntent
        )
        return true
    }

    private fun getPendingIntent(requestCode: Int,alarmId: String): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            requestCode,
            getIntent(alarmId),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun getIntent(alarmId:String): Intent {
        return Intent(context, AlarmReceiver::class.java).apply {
            putExtra("alarm_id", alarmId)
        }
    }

}