package com.purnendu.contactnamechanger.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.purnendu.contactnamechanger.components.CustomPhDialog

@Composable
fun DashBoard(
    modifier: Modifier = Modifier,
    viewModel: DashBoardViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val isPhNoDialogVisible = remember { mutableStateOf(false) }
    val phNo = remember { mutableStateOf("") }
    val isOperationGoing = remember { mutableStateOf(false) }
    val isDoneButtonClicked = remember { mutableStateOf(false) }
    val isContactExist = remember { mutableStateOf(false) }


    LaunchedEffect(key1 = isDoneButtonClicked.value)
    {

        if(!isDoneButtonClicked.value)
            return@LaunchedEffect

        if(phNo.value.isEmpty() || phNo.value.isBlank())
            return@LaunchedEffect

        isOperationGoing.value=true

        val contact=viewModel.isContactExist(phNo.value)
        println("hello")
        isPhNoDialogVisible.value=false
        if(contact==null)
            return@LaunchedEffect
        else
        {
            isContactExist.value=true
        }






    }

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


        if(isPhNoDialogVisible.value)
        {
            CustomPhDialog(
                title = "Enter Ph No",
                label = "Ph No",
                phNo = phNo.value,
                onPhNoChange = {phNo.value=it},
                onDoneButtonClick = { isDoneButtonClicked.value=true},
                isOperationGoing = isOperationGoing.value,
                isContactAvailable = isContactExist.value ,
                onCrossIconClick = { isPhNoDialogVisible.value=false }) {

            }
        }


        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {

        }

    }


}