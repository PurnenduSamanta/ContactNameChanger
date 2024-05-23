package com.purnendu.contactnamechanger.screen

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.purnendu.contactnamechanger.model.Contact
import com.purnendu.contactnamechanger.utils.contactOperation.isContactExist


class DashBoardViewModel(private val application: Application): AndroidViewModel(application)
{

    private val _isProgress = mutableStateOf(false)
    val isProgression get() = _isProgress

    suspend fun isContactExist(phNo:String):Contact?
    {
        return try {
            isContactExist(application,phNo)
        } catch (e:Exception) {
            null
        }
    }







}