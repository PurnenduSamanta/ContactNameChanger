package com.purnendu.contactnamechanger.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun  CustomPhDialog(
    title: String,
    label:String,
    phNo:String,
    modifiedPhNo:String,
    isOperationGoing:Boolean,
    isContactAvailable:Boolean,
    startingTime:String,
    endingTime:String,
    onPhNoChange:(String)->Unit,
    onCrossIconClick:()->Unit,
    onDoneButtonClick:()->Unit,
    onModifiedNoChange: (String) -> Unit,
    onSelectOfStartingTime:()->Unit,
    onSelectOfEndingTime:()->Unit
) {

    val rotation = animateFloatAsState(
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 400,
                easing = FastOutSlowInEasing,
            )), label = "animation"
    )

    Dialog(
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        onDismissRequest = {  })
    {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(10.dp),
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Icon(modifier = Modifier.clickable { onCrossIconClick() }, imageVector = Icons.Default.Clear, contentDescription = "clearIcon",tint=Color.Black)
            }


            Spacer(modifier = Modifier.height(10.dp))


            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(0.8f),
                    label = {Text(text = label)},
                    value = phNo,
                    onValueChange ={onPhNoChange(it) }
                )

                Spacer(modifier = Modifier.weight(0.025f))

                FilledTonalIconButton(onClick = {}) {

                    Box(modifier = Modifier
                        .weight(0.725f)
                        .background(Color.Cyan, shape = CircleShape)
                        .border(color = Color.Gray, width = 1.dp, shape = CircleShape)
                        .padding(10.dp)
                        .clip(CircleShape)
                        .clickable { onDoneButtonClick() }){
                       if(isOperationGoing)
                           Icon(modifier = Modifier
                               .size(30.dp)
                               .rotate(rotation.value), imageVector = Icons.Default.Refresh, tint = Color.Blue, contentDescription = "refreshIcon")
                        else
                           Icon(modifier = Modifier.size(30.dp), imageVector = Icons.Default.Done, tint = Color.Blue, contentDescription = "doneIcon")
                    }

                }
            }

            if(isContactAvailable)
            {
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = {Text(text = "Enter name to be modified")},
                    value = modifiedPhNo,
                    onValueChange ={ onModifiedNoChange(it)}
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

                    Button(modifier = Modifier.weight(0.45f), onClick = { onSelectOfStartingTime() }) {
                        Text(text = startingTime.ifEmpty { "Starting time" })
                    }

                    Spacer(modifier = Modifier.fillMaxWidth(0.1f))

                    Button(modifier = Modifier.weight(0.45f),onClick = { onSelectOfEndingTime() }) {
                        Text(text = endingTime.ifEmpty { "Ending time" })
                    }

                }


            }

        }

    }

}