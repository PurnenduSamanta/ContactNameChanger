package com.purnendu.contactnamechanger.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.purnendu.contactnamechanger.components.CustomPhDialog
import com.purnendu.contactnamechanger.components.TimePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoard(
    modifier: Modifier = Modifier,
    viewModel: DashBoardViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val isPhNoDialogVisible = remember { mutableStateOf(false) }
    val phNo = remember { mutableStateOf("") }
    val modifiedPhNo = remember { mutableStateOf("") }
    val startingTime = remember { mutableStateOf("") }
    val endingTime = remember { mutableStateOf("") }
    val isStartingTimePickerVisible = remember { mutableStateOf(false) }
    val isEndingTimePickerVisible = remember { mutableStateOf(false) }
    val startingTimePickerState = rememberTimePickerState()
    val endingTimePickerState = rememberTimePickerState()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton ={ FloatingActionButton(onClick = { isPhNoDialogVisible.value=true }) {

            Box(modifier = modifier
                .clip(CircleShape.copy(all = CornerSize(10.dp)))
                .padding(5.dp))
            Icon( modifier = Modifier.size(30.dp), imageVector = Icons.Default.Add, tint = Color.White, contentDescription = "add")
        }},
        floatingActionButtonPosition = FabPosition.End
    )
    {
            TimePicker(
                showDialog = isStartingTimePickerVisible.value,
                timePickerState = startingTimePickerState,
                onTimeSet = {
                    startingTime.value=it
                    isStartingTimePickerVisible.value=false
                }
            )
            {
                isStartingTimePickerVisible.value=false
            }
        TimePicker(
            showDialog = isEndingTimePickerVisible.value,
            timePickerState = endingTimePickerState,
            onTimeSet = {
                endingTime.value=it
                isEndingTimePickerVisible.value=false}
        )
        {
            isEndingTimePickerVisible.value=false
        }


        if(isPhNoDialogVisible.value)
        {
            CustomPhDialog(
                title = "Enter Mobile No",
                label = "Mobile No",
                phNo = phNo.value,
                modifiedPhNo = modifiedPhNo.value,
                onPhNoChange = {phNo.value=it},
                startingTime = startingTime.value,
                endingTime = endingTime.value,
                onDoneButtonClick = {
                    if(phNo.value.isEmpty() || phNo.value.isBlank())
                        return@CustomPhDialog
                    viewModel.isContactExist(phNo.value)
                },
                onModifiedNoChange = {modifiedPhNo.value=it},
                isOperationGoing = false,
                isContactAvailable = viewModel.isContactExist.value!=null ,
                onCrossIconClick = {
                    phNo.value=""
                    modifiedPhNo.value=""
                    viewModel.setContactStatusToNull()
                    isPhNoDialogVisible.value=false
                    startingTime.value=""
                    endingTime.value=""
                    isStartingTimePickerVisible.value=false
                    isEndingTimePickerVisible.value=false
                },
                onSelectOfStartingTime = { isStartingTimePickerVisible.value=true},
                onSelectOfEndingTime = {isEndingTimePickerVisible.value=true})
        }


        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {

        }

    }


}