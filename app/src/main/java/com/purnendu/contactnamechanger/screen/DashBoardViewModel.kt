package com.purnendu.contactnamechanger.screen

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.purnendu.contactnamechanger.database.AlarmDatabase
import com.purnendu.contactnamechanger.database.models.Alarm
import com.purnendu.contactnamechanger.model.Contact
import com.purnendu.contactnamechanger.utils.alarmManager.AlarmManager
import com.purnendu.contactnamechanger.utils.contactOperation.isContactExist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.random.Random


class DashBoardViewModel(private val application: Application): AndroidViewModel(application)
{


    private val alarmDao = AlarmDatabase.getDataBase(application).AlarmDao()

    private val _isContactExist = mutableStateOf<Contact?>(null)
    val isContactExist get() = _isContactExist

    private val _isAlarmSetSuccessfully = mutableStateOf(false)
    val isAlarmSetSuccessfully get() = _isAlarmSetSuccessfully

    fun setContactStatusToNull() {_isContactExist.value = null }

    fun isContactExist(phNo:String) = viewModelScope.launch (Dispatchers.IO) { _isContactExist.value = isContactExist(application, phNo) }
    fun setAlarm(alarmName:String, startAlarmTime:Long, endAlarmTime:Long, phNo:String,name:String, modifiedName:String)
    {
        _isAlarmSetSuccessfully.value=false
        val startAlarmId = UUID.randomUUID()
        val endAlarmId = UUID.randomUUID()
        val startAlarmRequestCode =  Random.nextInt()
        val endAlarmRequestCode = Random.nextInt()

        val manager = AlarmManager(application)
        val isStartingAlarmSet = manager.schedule(startAlarmRequestCode, startAlarmTime, startAlarmId.toString(),phNo,modifiedName)
        val manager2 = AlarmManager(application)
        val isEndingAlarmSet =manager2.schedule(endAlarmRequestCode, endAlarmTime, endAlarmId.toString(),phNo,name)


        if(isStartingAlarmSet && isEndingAlarmSet)
        {
            viewModelScope.launch(Dispatchers.IO) {
                val insertionStatus = alarmDao.insertAlarm(
                    Alarm(
                        alarmId = UUID.randomUUID().toString(),
                        startingAlarmRequestCode = startAlarmRequestCode.toString(),
                        endingAlarmRequestCode = endAlarmRequestCode.toString(),
                        alarmName = alarmName

                    )
                )
                _isAlarmSetSuccessfully.value = insertionStatus>0

            }
        }
        else
            _isAlarmSetSuccessfully.value=false
    }

    fun getAlarms() = alarmDao.getAllAlarms()
    suspend fun getAlarm(alarmId:String) = alarmDao.getAlarm(alarmId)

    fun cancelAlarm(startingRequestCode:String,endingRequestCode:String)
    {
        val manager = AlarmManager(application)
        val manager2 = AlarmManager(application)
        manager.cancelAlarm(application,startingRequestCode.toInt())
        manager2.cancelAlarm(application,endingRequestCode.toInt())
        viewModelScope.launch(Dispatchers.IO) {
            alarmDao.deleteAlarm(startingRequestCode,endingRequestCode)
        }

    }
}