package com.purnendu.contactnamechanger.utils.alarmManager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class AlarmManager(private val context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun schedule(requestCode: Int, triggerTimeInMillis: Long, alarmId: String,phNo:String,displayName:String): Boolean {
        if (triggerTimeInMillis < System.currentTimeMillis()) {
            return false // Trigger time is in the past
        }

        val pendingIntent = getPendingIntent(requestCode, alarmId,phNo,displayName)

        try {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP, // Use RTC_WAKEUP to wake the device
                triggerTimeInMillis,
                pendingIntent
            )
            return true // Alarm was successfully scheduled
        } catch (e: SecurityException) {
            e.printStackTrace()
            return false // Failed to schedule alarm due to lack of permission
        }
    }

    private fun getPendingIntent(requestCode: Int,alarmId: String,phNo:String,displayName:String): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            requestCode,
            getIntent(alarmId,phNo,displayName),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun getIntent(alarmId:String,phNo:String,displayName:String): Intent {
        return Intent(context, AlarmReceiver::class.java).apply {
            putExtra("alarm_id", alarmId)
            putExtra("phoneNo", phNo)
            putExtra("displayName", displayName)
        }
    }
    fun cancelAlarm(context: Context, requestCode: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = getPendingIntent(context, requestCode)
        alarmManager.cancel(alarmIntent)
        // You may also want to cancel the PendingIntent
        alarmIntent.cancel()
    }
    private fun getPendingIntent(context: Context, requestCode: Int): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java)
        return PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

}