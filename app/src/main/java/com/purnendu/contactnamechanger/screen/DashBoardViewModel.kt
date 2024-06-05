package com.purnendu.contactnamechanger.screen

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.purnendu.contactnamechanger.model.Contact
import com.purnendu.contactnamechanger.utils.contactOperation.isContactExist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DashBoardViewModel(private val application: Application): AndroidViewModel(application)
{
    private val _isContactExist = mutableStateOf<Contact?>(null)
    val isContactExist get() = _isContactExist

    fun setContactStatusToNull() {_isContactExist.value = null }

    fun isContactExist(phNo:String) = viewModelScope.launch (Dispatchers.IO) { _isContactExist.value = isContactExist(application, phNo) }


}