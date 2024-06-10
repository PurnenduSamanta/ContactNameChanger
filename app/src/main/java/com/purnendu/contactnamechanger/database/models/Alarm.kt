package com.purnendu.contactnamechanger.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Alarm")
data class Alarm(
    @PrimaryKey
    val alarmId: String,
    val startingAlarmRequestCode: String,
    val endingAlarmRequestCode: String,
    val alarmName: String,
    val startingAlarmTime: String,
    val endingAlarmTime: String,
    val phNo:String,
    val modifiedName:String
)