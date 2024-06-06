package com.purnendu.contactnamechanger.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.purnendu.contactnamechanger.utils.getCustomTimeInMillis

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePicker(
    showDialog: Boolean,
    timePickerState: TimePickerState,
    onTimeSet: (formattedTime: String,timeInMilliSec:Long) -> Unit,
    onDismissRequest: () -> Unit,

    ) {

    if(!showDialog)
        return

    BasicAlertDialog(
        onDismissRequest = { onDismissRequest() },
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(size = 12.dp)
            ),
        properties = DialogProperties(
            usePlatformDefaultWidth = true,
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ) // set this so that the dialog occupies the full width and height
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = Color.LightGray.copy(alpha = 0.3f)
                )
                .padding(
                    top = 16.dp,
                    start = 12.dp,
                    end = 12.dp,
                    bottom = 0.dp
                ), // reduce the padding to make the dialog contents fit into the screen
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // time picker
            androidx.compose.material3.TimePicker(state = timePickerState)
            // buttons
            Row(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center // place buttons at the center
            ) {
                // dismiss button
                TextButton(onClick = { onDismissRequest() }) {
                    Text(text = "Dismiss")
                }

                Spacer(modifier = Modifier.width(20.dp))

                TextButton(
                    onClick = {
                        val mHour = timePickerState.hour
                        val mMinute = timePickerState.minute
                        val formattedTime = if (mHour in 0..9 && mMinute in 0..9)
                            "0$mHour:0$mMinute"
                        else if (mHour in 0..9)
                            "0$mHour:$mMinute"
                        else if (mMinute in 0..9)
                            "$mHour:0$mMinute"
                        else
                            "$mHour:$mMinute"

                        val timeInMilliSec = getCustomTimeInMillis(mHour,mMinute,0)
                        onTimeSet(formattedTime,timeInMilliSec)
                    }
                ) {
                    Text(text = "Confirm")
                }
            }
        }
    }

}