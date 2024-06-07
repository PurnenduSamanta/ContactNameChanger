package com.purnendu.contactnamechanger.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.purnendu.contactnamechanger.database.models.Alarm
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(data: Alarm):Long

    @Query("DELETE FROM Alarm")
    suspend fun deleteAllAlarms()

    @Query("SELECT * FROM Alarm")
    fun getAllAlarms(): Flow<List<Alarm>>
    @Query("SELECT * FROM Alarm WHERE alarmId==:alarmId")
    suspend fun getAlarm(alarmId:String): Alarm?
    @Query("DELETE FROM Alarm where startingAlarmRequestCode ==:startingRequestCode AND endingAlarmRequestCode ==:endingRequestCode")
    suspend fun deleteAlarm(startingRequestCode:String,endingRequestCode:String)
}