package com.purnendu.contactnamechanger.screen.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.purnendu.contactnamechanger.components.CustomPhDialog
import com.purnendu.contactnamechanger.components.TimePicker
import com.purnendu.contactnamechanger.screen.dashboard.components.SingleDashBoardItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoard(
    modifier: Modifier = Modifier,
    viewModel: DashBoardViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val scope = rememberCoroutineScope()

    val isPhNoDialogVisible = remember { mutableStateOf(false) }
    val alarmName = remember { mutableStateOf("") }
    val phNo = remember { mutableStateOf("") }
    val modifiedName = remember { mutableStateOf("") }
    val startingTime = remember { mutableStateOf(Pair("", 0L)) }
    val endingTime = remember { mutableStateOf(Pair("", 0L)) }
    val isStartingTimePickerVisible = remember { mutableStateOf(false) }
    val isEndingTimePickerVisible = remember { mutableStateOf(false) }
    val startingTimePickerState = rememberTimePickerState(is24Hour = true)
    val endingTimePickerState = rememberTimePickerState(is24Hour = true)
    val alarmList = viewModel.getAlarms().collectAsState(initial = emptyList()).value


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = { isPhNoDialogVisible.value = true }) {

                Box(
                    modifier = modifier
                        .clip(CircleShape.copy(all = CornerSize(10.dp)))
                        .padding(5.dp)
                )
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.Default.Add,
                    tint = Color.White,
                    contentDescription = "add"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    )
    {
        TimePicker(
            showDialog = isStartingTimePickerVisible.value,
            timePickerState = startingTimePickerState,
            onTimeSet = { formattedTime, timeInMilliSecond ->
                startingTime.value = Pair(formattedTime, timeInMilliSecond)
                isStartingTimePickerVisible.value = false
            }
        )
        {
            isStartingTimePickerVisible.value = false
        }
        TimePicker(
            showDialog = isEndingTimePickerVisible.value,
            timePickerState = endingTimePickerState,
            onTimeSet = { formattedTime, timeInMilliSecond ->
                endingTime.value = Pair(formattedTime, timeInMilliSecond)
                isEndingTimePickerVisible.value = false
            }
        )
        {
            isEndingTimePickerVisible.value = false
        }


        if (isPhNoDialogVisible.value) {
            CustomPhDialog(
                title = "Select Contact",
                label = "Mobile No",
                phNo = phNo.value,
                alarmName = alarmName.value,
                modifiedName = modifiedName.value,
                onPhNoChange = { phNo.value = it },
                startingTime = startingTime.value.first,
                endingTime = endingTime.value.first,
                onAlarmNameChange = { alarmName.value = it },
                onDoneButtonClick = {
                    if (phNo.value.isEmpty() || phNo.value.isBlank())
                        return@CustomPhDialog
                    viewModel.isContactExist(phNo.value)
                },
                onModifiedNameChange = { modifiedName.value = it },
                isOperationGoing = false,
                isContactAvailable = viewModel.isContactExist.value != null,
                onCrossIconClick = {
                    phNo.value = ""
                    alarmName.value = ""
                    modifiedName.value = ""
                    viewModel.setContactStatusToNull()
                    isPhNoDialogVisible.value = false
                    startingTime.value = Pair("", 0L)
                    endingTime.value = Pair("", 0L)
                    isStartingTimePickerVisible.value = false
                    isEndingTimePickerVisible.value = false
                },
                onSelectOfStartingTime = { isStartingTimePickerVisible.value = true },
                onSelectOfEndingTime = { isEndingTimePickerVisible.value = true },
                onSaveButtonClick = {
                    viewModel.setAlarm(
                        alarmName = alarmName.value,
                        startAlarmTime = startingTime.value.second,
                        endAlarmTime = endingTime.value.second,
                        phNo = phNo.value,
                        name = viewModel.isContactExist.value?.name ?: "",
                        modifiedName = modifiedName.value
                    )


                    phNo.value = ""
                    alarmName.value = ""
                    modifiedName.value = ""
                    viewModel.setContactStatusToNull()
                    isPhNoDialogVisible.value = false
                    startingTime.value = Pair("", 0L)
                    endingTime.value = Pair("", 0L)
                    isStartingTimePickerVisible.value = false
                    isEndingTimePickerVisible.value = false
                })
        }


        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            LazyColumn(modifier = Modifier.fillMaxWidth().padding(10.dp))
            {
                itemsIndexed(alarmList)
                { index, item ->

                    SingleDashBoardItem(
                        alarmName = item.alarmName,
                        phNo = item.phNo,
                        startingTime = item.startingAlarmTime,
                        endingTime = item.endingAlarmTime,
                        onDeleteClick = {
                            viewModel.cancelAlarm(
                            startingRequestCode = item.startingAlarmRequestCode,
                            endingRequestCode = item.endingAlarmRequestCode
                        )}
                    )

                    if (index != alarmList.lastIndex)
                        Spacer(modifier = Modifier.height(10.dp))


                }
            }

        }

    }


}